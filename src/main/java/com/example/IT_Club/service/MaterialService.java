package com.example.IT_Club.service;

import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;

import java.util.List;

public interface MaterialService {
    MaterialResponse create(MaterialRequest materialRequest);
    void delete(Long id);
    List<MaterialResponse> all(int page, int size);
}
