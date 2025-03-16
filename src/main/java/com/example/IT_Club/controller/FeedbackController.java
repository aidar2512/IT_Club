package com.example.IT_Club.controller;

import com.example.IT_Club.model.dto.feedback.FeedbackResponse;
import com.example.IT_Club.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/{id}")
    public FeedbackResponse leaveFeedback(
            @RequestParam String message,
            @PathVariable Long id
    ) {
        return feedbackService.leaveFeedback(message, id);
    }

    @GetMapping
    public List<FeedbackResponse> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedbackService.all(page, size);
    }
}
