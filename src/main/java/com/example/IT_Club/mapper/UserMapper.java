package com.example.IT_Club.mapper;

import com.example.IT_Club.model.dto.user.UserRequest;
import com.example.IT_Club.model.dto.user.UserResponse;
import com.example.IT_Club.model.domain.User;

public interface UserMapper {
    User toUser(UserRequest request);
    UserResponse toResponse(User user);
}
