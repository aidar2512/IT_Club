package com.example.IT_Club.mapper;

import com.example.IT_Club.model.domain.Category;
import com.example.IT_Club.model.domain.Event;
import com.example.IT_Club.model.dto.event.EventRequest;
import com.example.IT_Club.model.dto.event.EventResponse;

import java.util.List;

public interface EventMapper {
    Event toEvent(Event event, EventRequest request, Category category);
    EventResponse toResponse(Event event);
    List<EventResponse> toResponseList(List<Event> eventList);
}
