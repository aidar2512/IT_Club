package com.example.IT_Club.service;

import com.example.IT_Club.model.dto.project.ProjectRequest;
import com.example.IT_Club.model.dto.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse create(ProjectRequest projectRequest);
    List<ProjectResponse> all();
    ProjectResponse getProject(Long id);
    ProjectResponse addMember(Long projectId, List<String> emails);
    ProjectResponse removeMember(Long projectId, List<String> emails);
    void deleteProject(Long projectId);
}
