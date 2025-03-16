package com.example.IT_Club.service;

import com.example.IT_Club.model.dto.feedback.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse leaveFeedback(String message, Long id);
    List<FeedbackResponse> all(int page, int size);
}
