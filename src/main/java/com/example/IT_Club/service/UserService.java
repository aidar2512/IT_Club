package com.example.IT_Club.service;

import com.example.IT_Club.model.dto.user.UserRequest;
import com.example.IT_Club.model.dto.user.UserResponse;

public interface UserService {
    UserResponse create(UserRequest request);
    UserResponse getUser(Long id);
}
