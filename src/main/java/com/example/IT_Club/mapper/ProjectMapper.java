package com.example.IT_Club.mapper;

import com.example.IT_Club.model.domain.Project;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.project.ProjectRequest;
import com.example.IT_Club.model.dto.project.ProjectResponse;

import java.util.List;
import java.util.Set;

public interface ProjectMapper {
    Project toProject(ProjectRequest request, Set<User> members);
    ProjectResponse toResponse(Project project);
    List<ProjectResponse> toResponses(List<Project> projects);
}
