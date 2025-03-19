package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.UserMapper;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.user.UserRequest;
import com.example.IT_Club.model.dto.user.UserResponse;
import com.example.IT_Club.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest userRequest;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("password123");

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setRole(null);  // You can set the role if it's necessary
        user.setCreatedAt(LocalDateTime.now());

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("John Doe");
        userResponse.setEmail("john.doe@example.com");
        userResponse.setRole(null);  // You can set the role if it's necessary
        userResponse.setCreatedAt(user.getCreatedAt());
    }

    @Test
    void create() {
        // Given
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        UserResponse createdUser = userService.create(userRequest);

        // Then
        assertNotNull(createdUser);
        assertEquals(userResponse.getId(), createdUser.getId());
        assertEquals(userResponse.getName(), createdUser.getName());
        assertEquals(userResponse.getEmail(), createdUser.getEmail());

        verify(userMapper).toUser(userRequest);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void getUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        UserResponse foundUser = userService.getUser(1L);

        // Then
        assertNotNull(foundUser);
        assertEquals(userResponse.getId(), foundUser.getId());
        assertEquals(userResponse.getName(), foundUser.getName());
        assertEquals(userResponse.getEmail(), foundUser.getEmail());

        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(user);
    }

    @Test
    void getUser_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> userService.getUser(1L));
        assertEquals("User not found", exception.getMessage());
    }
}
