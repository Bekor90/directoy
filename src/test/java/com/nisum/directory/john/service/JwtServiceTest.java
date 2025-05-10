package com.nisum.directory.john.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import java.util.Date;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "my-very-secret-key-that-is-at-least-32-characters";
    private final long expirationMillis = 3600000;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(secret, expirationMillis);
    }

    @Test
    void generateToken_shouldReturnValidJwt() {
        String token = jwtService.generateToken("test@example.com");

        assertNotNull(token);
        assertTrue(jwtService.validateToken(token));
        assertEquals("test@example.com", jwtService.getSubject(token));
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.token.structure";
        assertFalse(jwtService.validateToken(invalidToken));
    }

    @Test
    void getSubject_shouldReturnCorrectSubject() {
        String token = jwtService.generateToken("user@example.com");
        String subject = jwtService.getSubject(token);

        assertEquals("user@example.com", subject);
    }

    @Test
    void validateToken_shouldReturnFalseForExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject("expired@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000))
                .setExpiration(new Date(System.currentTimeMillis() - 3600000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtService.generateToken("dummy").substring(0, 32).getBytes())
                .compact();

        assertFalse(jwtService.validateToken(expiredToken));
    }
}
