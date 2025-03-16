package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.UserMapper;
import com.example.IT_Club.model.dto.user.UserRequest;
import com.example.IT_Club.model.dto.user.UserResponse;
import com.example.IT_Club.repository.UserRepository;
import com.example.IT_Club.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {
        return userMapper.toResponse(userRepository.save(userMapper.toUser(request)));
    }

    @Override
    public UserResponse getUser(Long id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND)));
    }
}
