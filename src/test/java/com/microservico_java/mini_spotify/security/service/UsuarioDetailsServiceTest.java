package com.microservico_java.mini_spotify.security.service;

import com.microservico_java.mini_spotify.model.Usuario;
import com.microservico_java.mini_spotify.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDetailsService usuarioDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        // Arrange
        String email = "test@example.com";
        String senha = "senha123";
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail(email);
        usuarioMock.setSenha(senha);
        
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioMock));

        // Act
        UserDetails userDetails = usuarioDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(senha, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        // Arrange
        String email = "naoexiste@example.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> usuarioDetailsService.loadUserByUsername(email)
        );
        
        assertEquals("Usuário não encontrado: " + email, exception.getMessage());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_shouldMapCorrectRoles() {
        // Arrange
        String email = "admin@example.com";
        Usuario adminUsuario = new Usuario();
        adminUsuario.setEmail(email);
        adminUsuario.setSenha("senha123");
        
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(adminUsuario));

        // Act
        UserDetails userDetails = usuarioDetailsService.loadUserByUsername(email);

        // Assert
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        // Adicione mais asserts se houver lógica para diferentes tipos de usuário
    }
}