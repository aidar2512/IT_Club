package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.EventMapper;
import com.example.IT_Club.model.domain.Category;
import com.example.IT_Club.model.domain.Event;
import com.example.IT_Club.model.dto.event.EventRequest;
import com.example.IT_Club.model.dto.event.EventResponse;
import com.example.IT_Club.repository.CategoryRepository;
import com.example.IT_Club.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    private EventRequest eventRequest;
    private Event event;
    private EventResponse eventResponse;
    private Category category;

    @BeforeEach
    void setUp() {
        eventRequest = new EventRequest();
        eventRequest.setTitle("Test Event");
        eventRequest.setDescription("Description");
        eventRequest.setLocation("Location");

        category = new Category();
        category.setName("TEST");

        event = new Event();
        event.setTitle("Test Event");
        event.setCategory(category);

        eventResponse = new EventResponse();
        eventResponse.setTitle("Test Event");
        eventResponse.setCategory("TEST");
    }

    @Test
    void create_ShouldCreateEvent_WhenValidRequest() {
        when(eventRepository.findByTitle(eventRequest.getTitle())).thenReturn(Optional.empty());
        when(categoryRepository.findByName("TEST")).thenReturn(Optional.of(category));
        when(eventMapper.toEvent(any(Event.class), eq(eventRequest), eq(category))).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toResponse(event)).thenReturn(eventResponse);

        EventResponse response = eventService.create(eventRequest, "TEST");

        assertNotNull(response);
        assertEquals("Test Event", response.getTitle());
        verify(eventRepository).save(event);
    }

    @Test
    void create_ShouldThrowException_WhenEventAlreadyExists() {
        when(eventRepository.findByTitle(eventRequest.getTitle())).thenReturn(Optional.of(event));

        CustomException exception = assertThrows(CustomException.class, () ->
                eventService.create(eventRequest, "TEST")
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void update_ShouldUpdateEvent_WhenValidRequest() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(categoryRepository.findByName("TEST")).thenReturn(Optional.of(category));
        when(eventMapper.toEvent(event, eventRequest, category)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toResponse(event)).thenReturn(eventResponse);

        EventResponse response = eventService.update(eventRequest, 1L, "TEST");

        assertNotNull(response);
        assertEquals("Test Event", response.getTitle());
        verify(eventRepository).save(event);
    }

    @Test
    void delete_ShouldDeleteEvent_WhenIdExists() {
        doNothing().when(eventRepository).deleteById(1L);

        assertDoesNotThrow(() -> eventService.delete(1L));
        verify(eventRepository).deleteById(1L);
    }

    @Test
    void all_ShouldReturnPaginatedList() {
        List<Event> events = List.of(event);
        Page<Event> page = new PageImpl<>(events);
        when(eventRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(eventMapper.toResponseList(events)).thenReturn(List.of(eventResponse));

        List<EventResponse> responses = eventService.all(0, 10);

        assertEquals(1, responses.size());
        assertEquals("Test Event", responses.get(0).getTitle());
    }
}