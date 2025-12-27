package com.microservico_java.mini_spotify.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint entryPoint;

    @BeforeEach
    void setUp() {
        entryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void testCommenceShouldReturnUnauthorizedJson() throws Exception {
        // Mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // Simula o writer da resposta
        when(response.getWriter()).thenReturn(writer);

        // Executa o método
        entryPoint.commence(request, response, authException);

        // Verificações
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");

        writer.flush(); // garante que o conteúdo foi escrito

        String expectedJson = """
                {
                  "erro": "Acesso nao autorizado. Token ausente ou invalido. Faca login no AuthController e envie o token no cabecalho Autorizar."
                }
                """;

        assertEquals(expectedJson.trim(), stringWriter.toString().trim());
    }
}
