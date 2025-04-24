package com.example.autoservice.dto.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank String surname,
        @NotBlank String name,
        String patronymic,
        @Pattern(regexp = "\\+?\\d{10,15}") String phoneNumber,
        @Email @NotBlank String email,
        @Size(min = 6,max = 100) String password) {}