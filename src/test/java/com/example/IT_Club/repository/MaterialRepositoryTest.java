package com.example.IT_Club.repository;

import com.example.IT_Club.model.domain.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MaterialRepositoryTest {

    @Autowired
    private MaterialRepository materialRepository;

    private Material material;

    @BeforeEach
    void setUp() {
        material = new Material();
        material.setTitle("Spring Boot Guide");
        material.setContent("Comprehensive guide to Spring Boot");
        material.setTags("Spring,Boot,Java");
        material.setCreatedAt(LocalDateTime.now());
        materialRepository.save(material);
    }

    @Test
    void findAll_ShouldReturnAllMaterials() {
        // When
        List<Material> materials = materialRepository.findAll();

        // Then
        assertThat(materials).isNotEmpty();
        assertThat(materials).hasSize(1);
        assertThat(materials.get(0).getTitle()).isEqualTo("Spring Boot Guide");
        assertThat(materials.get(0).getContent()).isEqualTo("Comprehensive guide to Spring Boot");
    }
}