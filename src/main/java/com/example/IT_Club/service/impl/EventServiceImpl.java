package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.mapper.EventMapper;
import com.example.IT_Club.model.domain.Category;
import com.example.IT_Club.model.domain.Event;
import com.example.IT_Club.model.dto.event.EventRequest;
import com.example.IT_Club.model.dto.event.EventResponse;
import com.example.IT_Club.repository.CategoryRepository;
import com.example.IT_Club.repository.EventRepository;
import com.example.IT_Club.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse create(EventRequest eventRequest, String categoryName) {
        if (eventRepository.findByTitle(eventRequest.getTitle()).isPresent()) {
            throw new CustomException("Event with this title found", HttpStatus.CONFLICT);
        }

        Category category = categoryRepository.findByName(categoryName.toUpperCase()).orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND));

        return eventMapper.toResponse(eventRepository.save(eventMapper.toEvent(new Event(), eventRequest, category)));
    }

    @Override
    public EventResponse update(EventRequest eventRequest, Long id, String categoryName) {
        if (eventRepository.findByTitle(eventRequest.getTitle()).isPresent()) {
            throw new CustomException("Event with this title found", HttpStatus.CONFLICT);
        }

        Event event = eventRepository.findById(id).orElseThrow(() -> new CustomException("Event not found", HttpStatus.NOT_FOUND));

        if (categoryName != null) {
            Category category = categoryRepository.findByName(categoryName.toUpperCase()).orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND));
            return eventMapper.toResponse(eventRepository.save(eventMapper.toEvent(event, eventRequest, category)));
        } else {
            return eventMapper.toResponse(eventRepository.save(eventMapper.toEvent(event, eventRequest, event.getCategory())));
        }


    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventResponse> all(int page, int pageSize) {
        return eventMapper.toResponseList(eventRepository.findAll(PageRequest.of(page, pageSize)).toList());
    }
}
