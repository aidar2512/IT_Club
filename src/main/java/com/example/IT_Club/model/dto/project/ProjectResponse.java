package com.example.IT_Club.model.dto.project;

import com.example.IT_Club.model.dto.user.MemberResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {
    private String name;
    private String description;
    private String deploy;
    private String github;
    private List<MemberResponse> members;
}
