package com.example.IT_Club.service.impl;

import com.example.IT_Club.mapper.MaterialMapper;
import com.example.IT_Club.model.domain.Material;
import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;
import com.example.IT_Club.repository.MaterialRepository;
import com.example.IT_Club.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    @Override
    public MaterialResponse create(MaterialRequest materialRequest) {

        Material material = materialRepository.save(materialMapper.toMaterial(materialRequest));

        return materialMapper.toResponse(material);
    }

    @Override
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }

    @Override
    public List<MaterialResponse> all(int page, int size) {
        return materialMapper.toResponseList(materialRepository.findAll(PageRequest.of(page, size)).toList());
    }
}
