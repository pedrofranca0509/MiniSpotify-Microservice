package com.microservico_java.mini_spotify.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64 = Encoders.BASE64.encode(key.getEncoded());
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", base64);
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "murilo123";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void testInvalidTokenShouldReturnFalse() {
        String invalidToken = "invalid.token.value";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    void testTokenWithDifferentSecretShouldFailValidation() {
        String username = "murilo";
        String token = jwtUtil.generateToken(username);

        // Cria outra instância com outro segredo para validar incorretamente
        JwtUtil differentUtil = new JwtUtil();
        ReflectionTestUtils.setField(differentUtil, "jwtSecret", "outrachave123456789012345678901234");

        assertFalse(differentUtil.validateToken(token));
    }
}
