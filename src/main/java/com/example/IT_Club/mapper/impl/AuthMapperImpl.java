package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.mapper.AuthMapper;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.auth.AuthResponse;
import com.example.IT_Club.model.dto.auth.RegisterRequest;
import com.example.IT_Club.model.enums.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthMapperImpl implements AuthMapper {
    @Override
    public User toUser(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.MEMBER);
        return user;
    }

    @Override
    public AuthResponse toResponse(Long id, String token) {
        AuthResponse response = new AuthResponse();
        response.setId(id);
        response.setToken(token);
        return response;
    }
}