package com.nisum.directory.john.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UserRequest (
        @Schema(example = "Juan Rodriguez")
        @NotBlank
        String name,

        @Schema(example = "juan@rodriguez.org")
        @Email
        String email,

        @Schema(example = "Hunter2")
        @NotBlank
        String password,
        @NotEmpty List<PhoneRequest> phones
){}
