package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.ProjectMapper;
import com.example.IT_Club.model.domain.Project;
import com.example.IT_Club.model.domain.User;
import com.example.IT_Club.model.dto.project.ProjectRequest;
import com.example.IT_Club.model.dto.project.ProjectResponse;
import com.example.IT_Club.repository.ProjectRepository;
import com.example.IT_Club.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private ProjectRequest projectRequest;
    private Project project;
    private ProjectResponse projectResponse;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        projectRequest = new ProjectRequest();
        projectRequest.setName("Test Project");
        projectRequest.setDescription("Test Description");
        projectRequest.setDeploy("Test Deploy");
        projectRequest.setGithub("Test Github");
        projectRequest.setMembersEmail(Arrays.asList("test@example.com"));

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setDeploy("Test Deploy");
        project.setGithub("Test Github");
        project.setMembers(new HashSet<>(Collections.singletonList(user)));

        projectResponse = new ProjectResponse();
        projectResponse.setName("Test Project");
        projectResponse.setDescription("Test Description");
        projectResponse.setDeploy("Test Deploy");
        projectResponse.setGithub("Test Github");
    }

    @Test
    void create_shouldReturnProjectResponse_whenProjectIsCreated() {
        // Arrange
        when(projectRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(projectMapper.toProject(any(ProjectRequest.class), anySet())).thenReturn(project);
        when(projectMapper.toResponse(any(Project.class))).thenReturn(projectResponse);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        ProjectResponse result = projectService.create(projectRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        assertEquals("Test Description", result.getDescription());
        verify(projectRepository).save(any(Project.class));
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void create_shouldThrowException_whenProjectWithNameExists() {
        // Arrange
        when(projectRepository.findByName(anyString())).thenReturn(Optional.of(project));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> projectService.create(projectRequest));
        assertEquals("Project with this name is already exists", exception.getMessage());
    }

    @Test
    void all_shouldReturnListOfProjectResponses() {
        // Arrange
        List<Project> projects = Arrays.asList(project);
        when(projectRepository.findAll()).thenReturn(projects);
        when(projectMapper.toResponses(anyList())).thenReturn(Collections.singletonList(projectResponse));

        // Act
        List<ProjectResponse> result = projectService.all();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).getName());
        verify(projectRepository).findAll();
    }

    @Test
    void getProject_shouldReturnProjectResponse_whenProjectExists() {
        // Arrange
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectMapper.toResponse(any(Project.class))).thenReturn(projectResponse);

        // Act
        ProjectResponse result = projectService.getProject(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        verify(projectRepository).findById(1L);
    }

    @Test
    void getProject_shouldThrowException_whenProjectNotFound() {
        // Arrange
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> projectService.getProject(1L));
        assertEquals("Project not found", exception.getMessage());
    }

    @Test
    void addMember_shouldReturnUpdatedProjectResponse_whenMemberIsAdded() {
        // Arrange
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(projectMapper.toResponse(any(Project.class))).thenReturn(projectResponse);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        ProjectResponse result = projectService.addMember(1L, Arrays.asList("test@example.com"));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMembers().size());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void addMember_shouldThrowException_whenProjectNotFound() {
        // Arrange
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> projectService.addMember(1L, Arrays.asList("test@example.com")));
        assertEquals("Project not found", exception.getMessage());
    }

    @Test
    void removeMember_shouldReturnUpdatedProjectResponse_whenMemberIsRemoved() {
        // Arrange
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(projectMapper.toResponse(any(Project.class))).thenReturn(projectResponse);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        ProjectResponse result = projectService.removeMember(1L, Arrays.asList("test@example.com"));

        // Assert
        assertNotNull(result);
        assertTrue(result.getMembers().isEmpty());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void removeMember_shouldThrowException_whenProjectNotFound() {
        // Arrange
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> projectService.removeMember(1L, Arrays.asList("test@example.com")));
        assertEquals("Project not found", exception.getMessage());
    }

    @Test
    void deleteProject_shouldCallDeleteById_whenProjectExists() {
        // Arrange
        Long projectId = 1L;

        // Act
        projectService.deleteProject(projectId);

        // Assert
        verify(projectRepository).deleteById(projectId);
    }
}
