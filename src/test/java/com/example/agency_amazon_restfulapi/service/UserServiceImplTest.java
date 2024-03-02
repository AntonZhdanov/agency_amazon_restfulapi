package com.example.agency_amazon_restfulapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.agency_amazon_restfulapi.dto.user.UserRegistrationRequestDto;
import com.example.agency_amazon_restfulapi.dto.user.UserResponseDto;
import com.example.agency_amazon_restfulapi.mapper.UserMapper;
import com.example.agency_amazon_restfulapi.model.Role;
import com.example.agency_amazon_restfulapi.model.User;
import com.example.agency_amazon_restfulapi.repository.RoleRepository;
import com.example.agency_amazon_restfulapi.repository.UserRepository;
import com.example.agency_amazon_restfulapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegistrationRequestDto registrationRequest;
    private User user;
    private UserResponseDto userResponse;
    private Role role;

    @BeforeEach
    void setUp() {
        registrationRequest = new UserRegistrationRequestDto(
                "test@example.com", "password", "password", "Test", "User");
        user = new User("1", "test@example.com", "Test",
                "User", "encodedPassword", true, new HashSet<>());
        userResponse = new UserResponseDto("1", "test@example.com", "Test", "User");
        role = new Role("1", Role.RoleName.ROLE_USER);

        when(userMapper.toModel(registrationRequest)).thenReturn(user);
        when(passwordEncoder.encode(registrationRequest.password())).thenReturn("encodedPassword");
        when(roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userResponse);
    }

    @Test
    void register_shouldCreateAndReturnUser() {
        UserResponseDto result = userService.register(registrationRequest);

        assertThat(result).isEqualTo(userResponse);
        verify(userRepository).findByEmail(registrationRequest.email());
        verify(userRepository).save(user);
    }

    @Test
    void register_shouldEncodePassword() {
        userService.register(registrationRequest);

        verify(passwordEncoder).encode(registrationRequest.password());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void register_shouldAssignDefaultRoleToUser() {
        userService.register(registrationRequest);

        assertTrue(user.getRoles().contains(role));
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void register_shouldReturnUserResponseDtoWithCorrectData() {
        UserResponseDto result = userService.register(registrationRequest);

        assertEquals(userResponse.id(), result.id());
        assertEquals(userResponse.email(), result.email());
        assertEquals(userResponse.name(), result.name());
        assertEquals(userResponse.lastName(), result.lastName());
    }
}
