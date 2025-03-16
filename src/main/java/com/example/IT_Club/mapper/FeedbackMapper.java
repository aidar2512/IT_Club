package com.example.IT_Club.mapper;

import com.example.IT_Club.model.domain.Feedback;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.feedback.FeedbackResponse;

import java.util.List;

public interface FeedbackMapper {
    Feedback toFeedback(String message, User user);
    FeedbackResponse toResponse(Feedback feedback);
    List<FeedbackResponse> toResponseList(List<Feedback> feedbacks);
}
