package com.example.IT_Club.service.impl;

import com.example.IT_Club.mapper.MaterialMapper;
import com.example.IT_Club.model.domain.Material;
import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;
import com.example.IT_Club.repository.MaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

class MaterialServiceImplTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private MaterialMapper materialMapper;

    @InjectMocks
    private MaterialServiceImpl materialService;

    private MaterialRequest materialRequest;
    private Material material;
    private MaterialResponse materialResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        materialRequest = new MaterialRequest();
        materialRequest.setTitle("Test Material");
        materialRequest.setContent("Test Content");
        materialRequest.setTags("Test,Example");

        material = new Material();
        material.setId(1L);
        material.setTitle("Test Material");
        material.setContent("Test Content");
        material.setTags("Test,Example");

        materialResponse = new MaterialResponse();
        materialResponse.setTitle("Test Material");
        materialResponse.setContent("Test Content");
        materialResponse.setTags("Test,Example");
    }

    @Test
    void create_shouldReturnMaterialResponse_whenMaterialIsCreated() {
        // Arrange
        when(materialRepository.save(any(Material.class))).thenReturn(material);
        when(materialMapper.toResponse(any(Material.class))).thenReturn(materialResponse);

        // Act
        MaterialResponse result = materialService.create(materialRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Test Material", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertEquals("Test,Example", result.getTags());
        verify(materialRepository).save(any(Material.class));
        verify(materialMapper).toResponse(any(Material.class));
    }

    @Test
    void delete_shouldCallDeleteById_whenMaterialExists() {
        // Arrange
        Long materialId = 1L;

        // Act
        materialService.delete(materialId);

        // Assert
        verify(materialRepository).deleteById(materialId);
    }

    @Test
    void all_shouldReturnListOfMaterialResponses_whenMaterialsExist() {
        // Arrange
        List<Material> materials = List.of(material);
        when(materialRepository.findAll((Example<Material>) any())).thenReturn(materials);
        when(materialMapper.toResponseList(any())).thenReturn(List.of(materialResponse));

        // Act
        List<MaterialResponse> result = materialService.all(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Material", result.get(0).getTitle());
        assertEquals("Test Content", result.get(0).getContent());
        assertEquals("Test,Example", result.get(0).getTags());
        verify(materialRepository).findAll((Example<Material>) any());
    }

    @Test
    void all_shouldReturnEmptyList_whenNoMaterialsExist() {
        // Arrange
        when(materialRepository.findAll((Example<Material>) any())).thenReturn(List.of());
        when(materialMapper.toResponseList(any())).thenReturn(List.of());

        // Act
        List<MaterialResponse> result = materialService.all(0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(materialRepository).findAll((Example<Material>) any());
    }
}