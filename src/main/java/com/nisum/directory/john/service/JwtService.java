package com.nisum.directory.john.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtService {
    public String generateToken(String subject) {
        return UUID.randomUUID().toString();
    }
}

