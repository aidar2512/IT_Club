package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.ProjectMapper;
import com.example.IT_Club.model.domain.Project;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.project.ProjectRequest;
import com.example.IT_Club.model.dto.project.ProjectResponse;
import com.example.IT_Club.repository.ProjectRepository;
import com.example.IT_Club.repository.UserRepository;
import com.example.IT_Club.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponse create(ProjectRequest projectRequest) {
        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new CustomException("Project with this name is already exists", HttpStatus.CONFLICT);
        }

        Set<User> users = new HashSet<>();
        for (String email : projectRequest.getMembersEmail()) {
            users.add(userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found with email: " + email, HttpStatus.NOT_FOUND)));
        }

        return projectMapper.toResponse(projectRepository.save(projectMapper.toProject(projectRequest, users)));
    }

    @Override
    public List<ProjectResponse> all() {
        return projectMapper.toResponses(projectRepository.findAll());
    }

    @Override
    public ProjectResponse getProject(Long id) {
        return projectMapper.toResponse(projectRepository.findById(id).orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public ProjectResponse addMember(Long projectId, List<String> emails) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        Set<User> users = project.getMembers();
        for (String email : emails) {
            users.add(userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND)));
        }
        project.setMembers(users);

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse removeMember(Long projectId, List<String> emails) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        Set<User> users = project.getMembers();

        for (String email : emails) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found with email: " + email, HttpStatus.NOT_FOUND));
            users.remove(user);
        }
        project.setMembers(users);

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}

