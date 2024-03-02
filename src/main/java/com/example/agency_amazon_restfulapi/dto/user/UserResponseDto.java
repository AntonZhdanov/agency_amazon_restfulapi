package com.example.agency_amazon_restfulapi.dto.user;

public record UserResponseDto(
        String id,
        String email,
        String name,
        String lastName
) {}
