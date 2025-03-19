package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.model.domain.Project;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.project.ProjectRequest;
import com.example.IT_Club.model.dto.project.ProjectResponse;
import com.example.IT_Club.model.dto.user.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperImplTest {
    private ProjectMapperImpl projectMapper;

    @BeforeEach
    void setUp() {
        projectMapper = new ProjectMapperImpl();
    }

    @Test
    void toProject() {
        ProjectRequest request = new ProjectRequest();
        request.setName("Test Project");
        request.setDescription("Test Description");
        request.setDeploy("http://deploy.link");
        request.setGithub("http://github.link");

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Project project = projectMapper.toProject(request, Set.of(user));

        assertEquals(request.getName(), project.getName());
        assertEquals(request.getDescription(), project.getDescription());
        assertEquals(request.getDeploy(), project.getDeploy());
        assertEquals(request.getGithub(), project.getGithub());
        assertEquals(1, project.getMembers().size());
    }

    @Test
    void toResponse() {
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setDeploy("http://deploy.link");
        project.setGithub("http://github.link");

        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        project.setMembers(Set.of(user));

        ProjectResponse response = projectMapper.toResponse(project);

        assertEquals(project.getName(), response.getName());
        assertEquals(project.getDescription(), response.getDescription());
        assertEquals(project.getDeploy(), response.getDeploy());
        assertEquals(project.getGithub(), response.getGithub());
        assertEquals(1, response.getMembers().size());
    }

    @Test
    void toResponses() {
        Project project1 = new Project();
        project1.setName("Project 1");
        project1.setDescription("Description 1");

        Project project2 = new Project();
        project2.setName("Project 2");
        project2.setDescription("Description 2");

        List<ProjectResponse> responses = projectMapper.toResponses(List.of(project1, project2));

        assertEquals(2, responses.size());
        assertEquals("Project 1", responses.get(0).getName());
        assertEquals("Project 2", responses.get(1).getName());
    }
}