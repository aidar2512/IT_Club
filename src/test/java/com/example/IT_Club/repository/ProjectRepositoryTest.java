package com.example.IT_Club.repository;

import com.example.IT_Club.model.domain.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testSaveAndFindByName() {
        // Arrange
        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setDeploy("http://test.deploy");
        project.setGithub("http://test.github");

        // Act
        projectRepository.save(project);
        Optional<Project> foundProject = projectRepository.findByName("Test Project");

        // Assert
        assertTrue(foundProject.isPresent());
        assertEquals("Test Project", foundProject.get().getName());
        assertEquals("Test Description", foundProject.get().getDescription());
    }

    @Test
    void testFindByNameNotFound() {
        Optional<Project> foundProject = projectRepository.findByName("Nonexistent Project");
        assertFalse(foundProject.isPresent());
    }
}