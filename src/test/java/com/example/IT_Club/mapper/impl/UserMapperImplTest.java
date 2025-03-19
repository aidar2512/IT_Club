package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.user.UserRequest;
import com.example.IT_Club.model.dto.user.UserResponse;
import com.example.IT_Club.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void toUser() {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("johndoe@example.com");
        request.setPassword("password123");

        User user = userMapper.toUser(request);

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(Role.MEMBER, user.getRole());
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void toResponse() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setRole(Role.MEMBER);
        user.setCreatedAt(LocalDateTime.now());

        UserResponse response = userMapper.toResponse(user);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("johndoe@example.com", response.getEmail());
        assertEquals("MEMBER", response.getRole());
        assertNotNull(response.getCreatedAt());
    }
}