package com.example.IT_Club.mapper;

import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.auth.AuthResponse;
import com.example.IT_Club.model.dto.auth.RegisterRequest;

public interface AuthMapper {
    User toUser(RegisterRequest request);
    AuthResponse toResponse(Long id, String token);
}
