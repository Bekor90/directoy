package com.nisum.directory.john.controller;

import com.nisum.directory.john.dto.request.UserRequest;
import com.nisum.directory.john.dto.response.ErrorResponse;
import com.nisum.directory.john.dto.response.UserResponse;
import com.nisum.directory.john.security.exception.RequiredValueException;
import com.nisum.directory.john.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Operation(summary = "Registrar nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Correo ya registrado o datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        try {
            logger.info("Processing user registration: "+request.name());
            return ResponseEntity.ok(userService.registerUser(request));
        } catch (IllegalArgumentException | RequiredValueException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse( e.getMessage()));
        }
    }
}

