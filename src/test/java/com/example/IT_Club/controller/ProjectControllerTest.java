package com.example.IT_Club.controller;

import com.example.IT_Club.model.dto.project.ProjectRequest;
import com.example.IT_Club.model.dto.project.ProjectResponse;
import com.example.IT_Club.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    void create() throws Exception {
        ProjectRequest request = new ProjectRequest();
        request.setName("New Project");
        ProjectResponse response = new ProjectResponse();
        response.setName("New Project");

        when(projectService.create(any())).thenReturn(response);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Project"));
    }

    @Test
    void all() throws Exception {
        when(projectService.all()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk());
    }

    @Test
    void getProject() throws Exception {
        ProjectResponse response = new ProjectResponse();
        response.setName("Existing Project");

        when(projectService.getProject(1L)).thenReturn(response);

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Existing Project"));
    }

    @Test
    void addMember() throws Exception {
        ProjectResponse response = new ProjectResponse();
        response.setName("Updated Project");

        when(projectService.addMember(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/projects/add-members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"user@example.com\"]"))
                .andExpect(status().isOk());
    }

    @Test
    void removeMember() throws Exception {
        ProjectResponse response = new ProjectResponse();
        response.setName("Updated Project");

        when(projectService.removeMember(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/projects/remove-members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"user@example.com\"]"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProject() throws Exception {
        doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isOk());
    }
}
