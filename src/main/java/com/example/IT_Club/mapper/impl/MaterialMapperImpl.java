package com.example.IT_Club.mapper.impl;

import com.example.IT_Club.mapper.MaterialMapper;
import com.example.IT_Club.model.domain.Material;
import com.example.IT_Club.model.dto.material.MaterialRequest;
import com.example.IT_Club.model.dto.material.MaterialResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialMapperImpl implements MaterialMapper {
    @Override
    public Material toMaterial(MaterialRequest request) {
        Material material = new Material();
        material.setTitle(request.getTitle());
        material.setContent(request.getContent());
        material.setTags(request.getTags());
        material.setCreatedAt(LocalDateTime.now());
        return material;
    }

    @Override
    public MaterialResponse toResponse(Material material) {
        MaterialResponse response = new MaterialResponse();
        response.setTitle(material.getTitle());
        response.setContent(material.getContent());
        response.setTags(material.getTags());
        response.setCreatedAt(material.getCreatedAt());
        return response;
    }

    @Override
    public List<MaterialResponse> toResponseList(List<Material> materialList) {
        List<MaterialResponse> responseList = new ArrayList<>();
        for (Material material : materialList) {
            responseList.add(toResponse(material));
        }
        return responseList;
    }
}