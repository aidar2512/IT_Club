package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.model.domain.Feedback;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.feedback.FeedbackResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackMapperImplTest {

    private FeedbackMapperImpl feedbackMapper;

    @BeforeEach
    void setUp() {
        feedbackMapper = new FeedbackMapperImpl();
    }

    @Test
    void toFeedback() {
        User user = new User();
        user.setEmail("test@example.com");
        String message = "This is a feedback message";

        Feedback feedback = feedbackMapper.toFeedback(message, user);

        assertNotNull(feedback);
        assertEquals(message, feedback.getMessage());
        assertEquals(user, feedback.getUser());
        assertNotNull(feedback.getCreatedAt());
    }

    @Test
    void toResponse() {
        User user = new User();
        user.setEmail("test@example.com");

        Feedback feedback = new Feedback();
        feedback.setMessage("This is a feedback message");
        feedback.setUser(user);
        feedback.setCreatedAt(LocalDateTime.now());

        FeedbackResponse response = feedbackMapper.toResponse(feedback);

        assertNotNull(response);
        assertEquals("test@example.com", response.getUserEmail());
        assertEquals("This is a feedback message", response.getMessage());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void toResponseList() {
        User user = new User();
        user.setEmail("test@example.com");

        Feedback feedback1 = new Feedback();
        feedback1.setMessage("Feedback 1");
        feedback1.setUser(user);
        feedback1.setCreatedAt(LocalDateTime.now());

        Feedback feedback2 = new Feedback();
        feedback2.setMessage("Feedback 2");
        feedback2.setUser(user);
        feedback2.setCreatedAt(LocalDateTime.now());

        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(feedback1);
        feedbackList.add(feedback2);

        List<FeedbackResponse> responseList = feedbackMapper.toResponseList(feedbackList);

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("Feedback 1", responseList.get(0).getMessage());
        assertEquals("Feedback 2", responseList.get(1).getMessage());
    }
}