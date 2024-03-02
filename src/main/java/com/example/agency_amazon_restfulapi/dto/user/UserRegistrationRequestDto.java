package com.example.agency_amazon_restfulapi.dto.user;

import com.example.agency_amazon_restfulapi.security.validation.FieldMatch;
import com.example.agency_amazon_restfulapi.security.validation.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords don't match!"
)
public record UserRegistrationRequestDto(
        @Email String email,
        @NotEmpty @PasswordValidator String password,
        String repeatPassword,
        @NotEmpty String name,
        @NotEmpty String lastName
) {}
