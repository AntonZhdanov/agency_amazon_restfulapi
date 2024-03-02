package com.example.agency_amazon_restfulapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.agency_amazon_restfulapi.dto.user.UserLoginRequestDto;
import com.example.agency_amazon_restfulapi.dto.user.UserLoginResponseDto;
import com.example.agency_amazon_restfulapi.dto.user.UserRegistrationRequestDto;
import com.example.agency_amazon_restfulapi.dto.user.UserResponseDto;
import com.example.agency_amazon_restfulapi.security.AuthenticationService;
import com.example.agency_amazon_restfulapi.security.JwtUtil;
import com.example.agency_amazon_restfulapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void login_ShouldReturnTokenForValidCredentials() throws Exception {
        UserLoginRequestDto requestDto = new UserLoginRequestDto("user@example.com", "password");
        UserLoginResponseDto responseDto = new UserLoginResponseDto("token");

        when(authenticationService.authenticate(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));

        verify(authenticationService).authenticate(requestDto);
    }

    @Test
    void register_ShouldCreateUserAndReturnUserData() throws Exception {
        UserRegistrationRequestDto requestDto =
                new UserRegistrationRequestDto("user@example.com", "password",
                        "password", "User", "Test");
        UserResponseDto responseDto =
                new UserResponseDto("1", "user@example.com", "User", "Test");

        when(userService.register(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .json("{\"id\":\"1\",\"email\":\"user@example.com\",\"name\":\"User\",\"lastName\":\"Test\"}"));

        verify(userService).register(requestDto);
    }

    @Test
    void register_ShouldReturnUserResponseWhenRegistrationIsSuccessful() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "user@example.com", "password", "password",
                "User", "Example");
        UserResponseDto responseDto = new UserResponseDto("1", "user@example.com",
                "User", "Example");

        when(userService.register(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.name").value("User"))
                .andExpect(jsonPath("$.lastName").value("Example"));

        verify(userService).register(requestDto);
    }

    @Test
    void login_ShouldReturnBadRequestWhenCredentialsAreInvalid() throws Exception {
        UserLoginRequestDto requestDto = new UserLoginRequestDto("", "");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verify(authenticationService, never()).authenticate(any(UserLoginRequestDto.class));
    }

    @Test
    void register_ShouldReturnBadRequestWhenRegistrationDataIsInvalid() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "", "", "", "", "");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any(UserRegistrationRequestDto.class));
    }
}
