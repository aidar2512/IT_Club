package com.example.IT_Club.model.dto.event;

import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    private String title;
    private String description;
    @Future
    private LocalDateTime startTime;
    private String location;
}
