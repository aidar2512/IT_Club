package com.example.IT_Club.mapper;

import com.example.IT_Club.model.domain.Material;
import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;

import java.util.List;

public interface MaterialMapper {
    Material toMaterial(MaterialRequest request);
    MaterialResponse toResponse(Material material);
    List<MaterialResponse> toResponseList(List<Material> materialList);
}
