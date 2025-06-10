package com.microservico_java.mini_spotify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // atenção: aqui são dois asteriscos para "todos os endpoints"
                        .allowedOrigins("http://localhost:8080/login/**", "http://localhost:8080/index.html") // substitua pela URL do seu front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // métodos que você quer liberar
                        .allowedHeaders("*")  // libera todos os headers
                        .allowCredentials(true); // se precisar enviar cookies ou auth
            }
        };
    }
}
