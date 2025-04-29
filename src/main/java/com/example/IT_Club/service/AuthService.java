package com.example.IT_Club.service;

import com.example.IT_Club.model.dto.auth.AuthResponse;
import com.example.IT_Club.model.dto.auth.LoginRequest;
import com.example.IT_Club.model.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}