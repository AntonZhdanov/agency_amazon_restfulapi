package com.example.agency_amazon_restfulapi.mapper;

import com.example.agency_amazon_restfulapi.config.MapperConfiguration;
import com.example.agency_amazon_restfulapi.dto.user.UserRegistrationRequestDto;
import com.example.agency_amazon_restfulapi.dto.user.UserResponseDto;
import com.example.agency_amazon_restfulapi.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}
