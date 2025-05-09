package com.nisum.directory.john.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
        @Schema(example = "incomplete data")
        String message
) {
}
