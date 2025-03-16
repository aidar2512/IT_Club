package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.mapper.UserMapper;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.user.UserRequest;
import com.example.IT_Club.model.dto.user.UserResponse;
import com.example.IT_Club.model.enums.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole().toString());
        userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;
    }
}
