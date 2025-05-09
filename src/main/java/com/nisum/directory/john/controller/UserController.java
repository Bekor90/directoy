package com.nisum.directory.john.controller;

import com.nisum.directory.john.dto.request.UserRequest;
import com.nisum.directory.john.exception.RequiredValueException;
import com.nisum.directory.john.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        try {
            System.out.println("Logre entrar al enpoint");
            return ResponseEntity.ok(userService.registerUser(request));
        } catch (IllegalArgumentException | RequiredValueException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

