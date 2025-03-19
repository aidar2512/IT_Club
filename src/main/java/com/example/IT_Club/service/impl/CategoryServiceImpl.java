package com.example.IT_Club.service.impl;

import com.example.IT_Club.exception.CustomException;
import com.example.IT_Club.model.domain.Category;
import com.example.IT_Club.repository.CategoryRepository;
import com.example.IT_Club.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public String addCategory(String category) {
        if (categoryRepository.findByName(category.toUpperCase()).isPresent()) {
            throw new CustomException("Category found", HttpStatus.CONFLICT);
        }

        Category eventCategory = new Category();
        eventCategory.setName(category.toUpperCase());
        categoryRepository.save(eventCategory);

        return eventCategory.getName();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public String updateCategory(String category, Long id) {

        if (categoryRepository.findByName(category.toUpperCase()).isPresent()) {
            throw new CustomException("Category found", HttpStatus.CONFLICT);
        }

        Category eventCategory = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND));
        eventCategory.setName(category.toUpperCase());
        categoryRepository.save(eventCategory);

        return eventCategory.getName();
    }

    @Override
    public List<String> getAllCategories(int page, int size) {
        List<String> categories = new ArrayList<>();
        List<Category> eventCategories = categoryRepository.findAll(PageRequest.of(page, size)).toList();

        for (Category category : eventCategories) {
            categories.add(category.getName());
        }
        return categories;
    }
}
