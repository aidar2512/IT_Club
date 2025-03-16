package com.example.IT_Club.service;

import com.example.IT_Club.model.dto.event.EventRequest;
import com.example.IT_Club.model.dto.event.EventResponse;

import java.util.List;

public interface EventService {
    EventResponse create(EventRequest eventRequest, String category);
    EventResponse update(EventRequest eventRequest, Long id, String category);
    void delete(Long id);
    List<EventResponse> all(int page, int pageSize);
}
