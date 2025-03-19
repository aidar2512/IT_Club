package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.model.domain.Category;
import com.example.IT_Club.model.domain.Event;
import com.example.IT_Club.model.dto.event.EventRequest;
import com.example.IT_Club.model.dto.event.EventResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperImplTest {

    private EventMapperImpl eventMapper;

    @BeforeEach
    void setUp() {
        eventMapper = new EventMapperImpl();
    }

    @Test
    void testToEvent() {
        // Arrange
        Event event = new Event();
        EventRequest request = new EventRequest();
        request.setTitle("Tech Conference");
        request.setDescription("A conference about technology");
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setLocation("New York");

        Category category = new Category();
        category.setName("Technology");

        // Act
        Event mappedEvent = eventMapper.toEvent(event, request, category);

        // Assert
        assertEquals("Tech Conference", mappedEvent.getTitle());
        assertEquals("A conference about technology", mappedEvent.getDescription());
        assertEquals("New York", mappedEvent.getLocation());
        assertEquals("Technology", mappedEvent.getCategory().getName());
    }

    @Test
    void testToResponse() {
        // Arrange
        Event event = new Event();
        event.setTitle("AI Summit");
        event.setDescription("A summit on AI advancements");
        event.setStartTime(LocalDateTime.now().plusDays(2));
        event.setLocation("San Francisco");

        Category category = new Category();
        category.setName("Artificial Intelligence");
        event.setCategory(category);

        // Act
        EventResponse response = eventMapper.toResponse(event);

        // Assert
        assertEquals("AI Summit", response.getTitle());
        assertEquals("A summit on AI advancements", response.getDescription());
        assertEquals("San Francisco", response.getLocation());
        assertEquals("Artificial Intelligence", response.getCategory());
    }

    @Test
    void testToResponseList() {
        // Arrange
        Event event1 = new Event();
        event1.setTitle("Cybersecurity Expo");
        event1.setDescription("An expo on cybersecurity trends");
        event1.setStartTime(LocalDateTime.now().plusDays(3));
        event1.setLocation("Los Angeles");
        Category category1 = new Category();
        category1.setName("Cybersecurity");
        event1.setCategory(category1);

        Event event2 = new Event();
        event2.setTitle("Blockchain Forum");
        event2.setDescription("Discussion on blockchain technology");
        event2.setStartTime(LocalDateTime.now().plusDays(4));
        event2.setLocation("Miami");
        Category category2 = new Category();
        category2.setName("Blockchain");
        event2.setCategory(category2);

        List<Event> events = List.of(event1, event2);

        // Act
        List<EventResponse> responses = eventMapper.toResponseList(events);

        // Assert
        assertEquals(2, responses.size());
        assertEquals("Cybersecurity Expo", responses.get(0).getTitle());
        assertEquals("Blockchain Forum", responses.get(1).getTitle());
    }
}