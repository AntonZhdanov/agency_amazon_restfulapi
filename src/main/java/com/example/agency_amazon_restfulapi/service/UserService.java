package com.example.agency_amazon_restfulapi.service;

import com.example.agency_amazon_restfulapi.dto.user.UserRegistrationRequestDto;
import com.example.agency_amazon_restfulapi.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
