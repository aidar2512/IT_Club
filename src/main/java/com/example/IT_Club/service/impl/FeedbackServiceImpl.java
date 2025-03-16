package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.FeedbackMapper;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.feedback.FeedbackResponse;
import com.example.IT_Club.repository.FeedbackRepository;
import com.example.IT_Club.repository.UserRepository;
import com.example.IT_Club.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackResponse leaveFeedback(String message, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        return feedbackMapper.toResponse(feedbackRepository.save(feedbackMapper.toFeedback(message, user)));
    }

    @Override
    public List<FeedbackResponse> all(int page, int size) {
        return feedbackMapper.toResponseList(feedbackRepository.findAll(PageRequest.of(page, size)).toList());
    }
}
