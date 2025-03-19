package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.FeedbackMapper;
import com.example.IT_Club.model.domain.Feedback;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.feedback.FeedbackResponse;
import com.example.IT_Club.repository.FeedbackRepository;
import com.example.IT_Club.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    void leaveFeedback_shouldReturnFeedbackResponse_whenUserExists() {
        // Arrange
        String message = "Great service!";
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setUserEmail(user.getEmail());
        feedbackResponse.setMessage(message);
        feedbackResponse.setCreatedAt(feedbackResponse.getCreatedAt());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(feedbackMapper.toFeedback(message, user)).thenReturn(new com.example.IT_Club.model.domain.Feedback());
        when(feedbackRepository.save(any())).thenReturn(new com.example.IT_Club.model.domain.Feedback());
        when(feedbackMapper.toResponse(any())).thenReturn(feedbackResponse);

        // Act
        FeedbackResponse result = feedbackService.leaveFeedback(message, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getUserEmail());
        assertEquals("Great service!", result.getMessage());
        verify(userRepository).findById(1L);
        verify(feedbackRepository).save(any());
    }

    @Test
    void leaveFeedback_shouldThrowCustomException_whenUserNotFound() {
        // Arrange
        String message = "Great service!";

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> feedbackService.leaveFeedback(message, 1L));
        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void all_shouldReturnListOfFeedbackResponse_whenFeedbackExists() {
        // Arrange
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setUserEmail(user.getEmail());
        feedbackResponse.setMessage("Feedback message");
        feedbackResponse.setCreatedAt(feedbackResponse.getCreatedAt());

        when(feedbackRepository.findAll((Example<Feedback>) any())).thenReturn(java.util.List.of(new com.example.IT_Club.model.domain.Feedback()));
        when(feedbackMapper.toResponseList(any())).thenReturn(java.util.List.of(feedbackResponse));

        // Act
        var result = feedbackService.all(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getUserEmail());
        verify(feedbackRepository).findAll((Example<Feedback>) any());
    }

    @Test
    void all_shouldReturnEmptyList_whenNoFeedback() {
        // Arrange
        when(feedbackRepository.findAll((Example<Feedback>) any())).thenReturn(java.util.Collections.emptyList());
        when(feedbackMapper.toResponseList(any())).thenReturn(java.util.Collections.emptyList());

        // Act
        var result = feedbackService.all(0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}