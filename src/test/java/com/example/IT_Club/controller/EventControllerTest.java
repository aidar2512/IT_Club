package com.example.IT_Club.controller;

import com.example.IT_Club.model.dto.event.EventRequest;
import com.example.IT_Club.model.dto.event.EventResponse;
import com.example.IT_Club.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void create() throws Exception {
        EventRequest request = new EventRequest();
        request.setTitle("Tech Meetup");
        request.setDescription("A tech event");
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setLocation("Main Hall");

        EventResponse response = new EventResponse();
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStartTime(request.getStartTime());
        response.setLocation(request.getLocation());
        response.setCategory("Technology");

        when(eventService.create(any(EventRequest.class), anyString())).thenReturn(response);

        mockMvc.perform(post("/events")
                        .param("category", "Technology")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tech Meetup"));
    }

    @Test
    void update() throws Exception {
        EventRequest request = new EventRequest();
        request.setTitle("Updated Tech Meetup");
        request.setDescription("Updated description");
        request.setStartTime(LocalDateTime.now().plusDays(2));
        request.setLocation("Updated Hall");

        EventResponse response = new EventResponse();
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStartTime(request.getStartTime());
        response.setLocation(request.getLocation());
        response.setCategory("Updated Technology");

        when(eventService.update(any(EventRequest.class), anyLong(), anyString())).thenReturn(response);

        mockMvc.perform(put("/events/1")
                        .param("category", "Updated Technology")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Tech Meetup"));
    }

    @Test
    void deleteEvent() throws Exception {
        doNothing().when(eventService).delete(anyLong());

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isOk());

        verify(eventService, times(1)).delete(1L);
    }

    @Test
    void all() throws Exception {
        EventResponse response = new EventResponse();
        response.setTitle("Tech Meetup");
        response.setDescription("A tech event");
        response.setStartTime(LocalDateTime.now().plusDays(1));
        response.setLocation("Main Hall");
        response.setCategory("Technology");

        when(eventService.all(anyInt(), anyInt())).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/events")
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Tech Meetup"));
    }
}