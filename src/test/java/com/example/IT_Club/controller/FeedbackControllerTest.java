package com.example.IT_Club.controller;

import com.example.IT_Club.model.dto.feedback.FeedbackResponse;
import com.example.IT_Club.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FeedbackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
    }

    @Test
    void leaveFeedback() throws Exception {
        FeedbackResponse response = new FeedbackResponse();
        response.setUserEmail("test@example.com");
        response.setMessage("Great event!");
        response.setCreatedAt(LocalDateTime.now());

        when(feedbackService.leaveFeedback(anyString(), anyLong())).thenReturn(response);

        mockMvc.perform(post("/feedbacks/1")
                        .param("message", "Great event!")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Great event!"));
    }

    @Test
    void all() throws Exception {
        FeedbackResponse response = new FeedbackResponse();
        response.setUserEmail("test@example.com");
        response.setMessage("Great event!");
        response.setCreatedAt(LocalDateTime.now());

        when(feedbackService.all(anyInt(), anyInt())).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/feedbacks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Great event!"));
    }
}