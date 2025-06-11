package com.microservico_java.mini_spotify.security;

import com.microservico_java.mini_spotify.security.jwt.JwtAuthenticationEntryPoint;
import com.microservico_java.mini_spotify.security.jwt.JwtAuthenticationFilter;
import com.microservico_java.mini_spotify.security.service.UsuarioDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setup() {
        UsuarioDetailsService usuarioDetailsService = Mockito.mock(UsuarioDetailsService.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = Mockito.mock(JwtAuthenticationFilter.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = Mockito.mock(JwtAuthenticationEntryPoint.class);

        securityConfig = new SecurityConfig(usuarioDetailsService, jwtAuthenticationFilter, jwtAuthenticationEntryPoint);
    }

    @Test
    void passwordEncoder_deveRetornarBCryptPasswordEncoder() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        String senha = "123456";
        String senhaCodificada = encoder.encode(senha);

        assertThat(encoder.matches(senha, senhaCodificada)).isTrue();
    }

    @Test
    void authenticationProvider_deveConfigurarUsuarioDetailsServiceCorretamente() {
        DaoAuthenticationProvider provider = securityConfig.authenticationProvider();

        assertThat(provider).isNotNull();
        assertThat(provider).isInstanceOf(DaoAuthenticationProvider.class);
    }
}
