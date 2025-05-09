package com.nisum.directory.john.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UserRequest (
    @NotBlank String name,
    @Email String email,
    @NotBlank String password,
    @NotEmpty List<PhoneRequest> phones
){}
