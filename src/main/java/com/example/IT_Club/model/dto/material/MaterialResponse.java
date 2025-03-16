package com.example.IT_Club.model.dto.material;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialResponse {
    private String title;
    private String content;
    private String tags;
    private LocalDateTime createdAt;
}
