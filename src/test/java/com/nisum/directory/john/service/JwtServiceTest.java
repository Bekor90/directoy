package com.nisum.directory.john.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void generateToken_shouldReturnNonNullUUIDString() {
        String token = jwtService.generateToken("user@example.com");

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertDoesNotThrow(() -> UUID.fromString(token));
    }

    @Test
    void generateToken_shouldGenerateUniqueTokens() {
        String token1 = jwtService.generateToken("user1@example.com");
        String token2 = jwtService.generateToken("user2@example.com");

        assertNotEquals(token1, token2);
    }
}
