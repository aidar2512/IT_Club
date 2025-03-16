package com.example.IT_Club.model.dto.feedback;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackResponse {
    private String userEmail;
    private String message;
    private LocalDateTime createdAt;
}
