package com.nisum.directory.john.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        @Schema(example = "6a8d3c4a-4cc9-4e3e-bc75-2d9c5c9890f4")
        UUID id,

        @Schema(example = "2025-05-07T12:34:56")
        LocalDateTime created,

        @Schema(example = "2025-05-07T12:34:56")
        LocalDateTime modified,

        @Schema(example = "2025-05-07T12:34:56")
        LocalDateTime lastLogin,

        @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(example = "true")
        boolean isActive
){ }
