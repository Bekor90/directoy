package com.nisum.directory.john.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PhoneRequest (
    @Schema(example = "1234567")
    @NotBlank
    String number,
    @Schema(example = "1")
    @NotBlank
    String citycode,
    @Schema(example = "57")
    @NotBlank
    String contrycode
){}
