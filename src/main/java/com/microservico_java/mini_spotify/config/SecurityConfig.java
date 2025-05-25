package com.microservico_java.mini_spotify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // permite acesso ao H2 Console
                        .anyRequest().permitAll() // permite todas as outras rotas
                )
                .csrf(csrf -> csrf.disable()) // desativa CSRF
                .headers(headers -> headers.disable()) // desativa todos os headers de segurança (libera uso de <iframe>)
                .build();
    }
}
