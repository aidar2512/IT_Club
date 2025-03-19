package com.example.IT_Club.repository;

import com.example.IT_Club.model.domain.Feedback;
import com.example.IT_Club.model.domain.User;
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
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        feedback = new Feedback();
        feedback.setMessage("Great event!");
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUser(user);
        feedbackRepository.save(feedback);
    }

    @Test
    void findAll_ShouldReturnAllFeedbacks() {
        // When
        List<Feedback> feedbackList = feedbackRepository.findAll();

        // Then
        assertThat(feedbackList).isNotEmpty();
        assertThat(feedbackList).hasSize(1);
        assertThat(feedbackList.get(0).getMessage()).isEqualTo("Great event!");
    }
}