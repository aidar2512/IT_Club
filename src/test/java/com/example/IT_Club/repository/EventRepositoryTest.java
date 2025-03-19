package com.example.IT_Club.repository;

import com.example.IT_Club.model.domain.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setTitle("Tech Conference");
        event.setDescription("Annual technology conference");
        event.setStartTime(LocalDateTime.now().plusDays(1));
        event.setLocation("New York");
        eventRepository.save(event);
    }

    @Test
    void findByTitle_ShouldReturnEvent_WhenTitleExists() {
        // When
        Optional<Event> foundEvent = eventRepository.findByTitle("Tech Conference");

        // Then
        assertThat(foundEvent).isPresent();
        assertThat(foundEvent.get().getTitle()).isEqualTo("Tech Conference");
        assertThat(foundEvent.get().getDescription()).isEqualTo("Annual technology conference");
    }

    @Test
    void findByTitle_ShouldReturnEmpty_WhenTitleDoesNotExist() {
        // When
        Optional<Event> foundEvent = eventRepository.findByTitle("Nonexistent Event");

        // Then
        assertThat(foundEvent).isEmpty();
    }
}