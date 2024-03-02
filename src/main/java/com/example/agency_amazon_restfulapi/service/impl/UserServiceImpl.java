package com.example.agency_amazon_restfulapi.service.impl;

import com.example.agency_amazon_restfulapi.dto.user.UserRegistrationRequestDto;
import com.example.agency_amazon_restfulapi.dto.user.UserResponseDto;
import com.example.agency_amazon_restfulapi.exception.RegistrationException;
import com.example.agency_amazon_restfulapi.mapper.UserMapper;
import com.example.agency_amazon_restfulapi.model.Role;
import com.example.agency_amazon_restfulapi.model.User;
import com.example.agency_amazon_restfulapi.repository.RoleRepository;
import com.example.agency_amazon_restfulapi.repository.UserRepository;
import com.example.agency_amazon_restfulapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.email()).isPresent()) {
            throw new RegistrationException("The email: " + requestDto.email()
                    + " is already in use! Please choose a different one.");
        }

        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));

        Role defaultRole = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new RegistrationException("Default role not found"));
        user.setRoles(Set.of(defaultRole));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
