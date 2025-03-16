package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.mapper.FeedbackMapper;
import com.example.IT_Club.model.domain.Feedback;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.feedback.FeedbackResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FeedbackMapperImpl implements FeedbackMapper {
    @Override
    public Feedback toFeedback(String message, User user) {
        Feedback feedback = new Feedback();
        feedback.setMessage(message);
        feedback.setUser(user);
        feedback.setCreatedAt(LocalDateTime.now());
        return feedback;
    }

    @Override
    public FeedbackResponse toResponse(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setUserEmail(feedback.getUser().getEmail());
        response.setMessage(feedback.getMessage());
        response.setCreatedAt(LocalDateTime.now());

        return response;
    }

    @Override
    public List<FeedbackResponse> toResponseList(List<Feedback> feedbacks) {
        List<FeedbackResponse> responseList = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            responseList.add(toResponse(feedback));
        }
        return responseList;
    }
}
