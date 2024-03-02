package com.example.agency_amazon_restfulapi.dto.user;

import com.example.agency_amazon_restfulapi.security.validation.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotEmpty
        @Size(min = 8, max = 20)
        @Email
        String email,
        @PasswordValidator
        String password
) {}
