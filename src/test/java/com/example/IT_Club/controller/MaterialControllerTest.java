package com.example.IT_Club.controller;

import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;
import com.example.IT_Club.service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaterialController.class)
class MaterialControllerTest {

    @Mock
    private MaterialService materialService;

    @InjectMocks
    private MaterialController materialController;

    private MockMvc mockMvc;

    private MaterialRequest materialRequest;
    private MaterialResponse materialResponse;

    @BeforeEach
    void setUp() {
        // Initialize test data
        materialRequest = new MaterialRequest();
        materialRequest.setTitle("Test Material");
        materialRequest.setContent("Test Content");
        materialRequest.setTags("tag1, tag2");

        materialResponse = new MaterialResponse();
        materialResponse.setTitle("Test Material");
        materialResponse.setContent("Test Content");
        materialResponse.setTags("tag1, tag2");
        materialResponse.setCreatedAt(null); // Can set an actual date if needed

        mockMvc = MockMvcBuilders.standaloneSetup(materialController).build();
    }

    @Test
    void create() throws Exception {
        // Given
        when(materialService.create(any(MaterialRequest.class))).thenReturn(materialResponse);

        // When & Then
        mockMvc.perform(post("/materials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Material\", \"content\":\"Test Content\", \"tags\":\"tag1, tag2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Material"))
                .andExpect(jsonPath("$.content").value("Test Content"))
                .andExpect(jsonPath("$.tags").value("tag1, tag2"));

        verify(materialService, times(1)).create(any(MaterialRequest.class));
    }

    @Test
    void all() throws Exception {
        // Given
        when(materialService.all(0, 10)).thenReturn(List.of(materialResponse));

        // When & Then
        mockMvc.perform(get("/materials")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Material"))
                .andExpect(jsonPath("$[0].content").value("Test Content"))
                .andExpect(jsonPath("$[0].tags").value("tag1, tag2"));

        verify(materialService, times(1)).all(0, 10);
    }
}