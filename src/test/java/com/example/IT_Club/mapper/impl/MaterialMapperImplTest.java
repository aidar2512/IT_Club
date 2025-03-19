package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.model.domain.Material;
import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialMapperImplTest {

    private MaterialMapperImpl materialMapper;

    @BeforeEach
    void setUp() {
        materialMapper = new MaterialMapperImpl();
    }

    @Test
    void toMaterial() {
        MaterialRequest request = new MaterialRequest();
        request.setTitle("Spring Boot Guide");
        request.setContent("Detailed tutorial on Spring Boot");
        request.setTags("Java, Spring");

        Material material = materialMapper.toMaterial(request);

        assertNotNull(material);
        assertEquals(request.getTitle(), material.getTitle());
        assertEquals(request.getContent(), material.getContent());
        assertEquals(request.getTags(), material.getTags());
        assertNotNull(material.getCreatedAt());
    }

    @Test
    void toResponse() {
        Material material = new Material();
        material.setTitle("Spring Boot Guide");
        material.setContent("Detailed tutorial on Spring Boot");
        material.setTags("Java, Spring");
        material.setCreatedAt(LocalDateTime.now());

        MaterialResponse response = materialMapper.toResponse(material);

        assertNotNull(response);
        assertEquals(material.getTitle(), response.getTitle());
        assertEquals(material.getContent(), response.getContent());
        assertEquals(material.getTags(), response.getTags());
        assertEquals(material.getCreatedAt(), response.getCreatedAt());
    }

    @Test
    void toResponseList() {
        Material material1 = new Material();
        material1.setTitle("Java Basics");
        material1.setContent("Introduction to Java");
        material1.setTags("Java, Programming");
        material1.setCreatedAt(LocalDateTime.now());

        Material material2 = new Material();
        material2.setTitle("Spring Boot");
        material2.setContent("Guide on Spring Boot");
        material2.setTags("Spring, Java");
        material2.setCreatedAt(LocalDateTime.now());

        List<MaterialResponse> responseList = materialMapper.toResponseList(List.of(material1, material2));

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("Java Basics", responseList.get(0).getTitle());
        assertEquals("Spring Boot", responseList.get(1).getTitle());
    }
}