package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.LoginRequestDTO;
import com.microservico_java.mini_spotify.security.jwt.JwtUtil;
import com.microservico_java.mini_spotify.security.service.UsuarioDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioDetailsService usuarioDetailsService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_deveRetornarToken_QuandoCredenciaisValidas() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO("usuario@teste.com", "senha123");
        UserDetails userDetails = new User("usuario@teste.com", "senha123", Collections.emptyList());
        
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(null); // O retorno real não é usado, apenas verifica se a autenticação ocorre
        when(usuarioDetailsService.loadUserByUsername("usuario@teste.com"))
            .thenReturn(userDetails);
        when(jwtUtil.generateToken("usuario@teste.com")).thenReturn("token.jwt.123");

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof AuthController.AuthResponse);
        assertEquals("token.jwt.123", ((AuthController.AuthResponse) response.getBody()).getToken());
        
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDetailsService, times(1)).loadUserByUsername("usuario@teste.com");
        verify(jwtUtil, times(1)).generateToken("usuario@teste.com");
    }

    @Test
    void login_deveRetornarUnauthorized_QuandoCredenciaisInvalidas() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO("usuario@teste.com", "senhaErrada");
        
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário ou senha inválidos", response.getBody());
        
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void login_deveRetornarInternalServerError_QuandoOcorrerErroInesperado() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO("usuario@teste.com", "senha123");
        
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new RuntimeException("Erro inesperado"));

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno", response.getBody());
        
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtil, never()).generateToken(anyString());
    }
}