package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateCategoryRequest;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveNewCategory(CreateCategoryRequest request, Long userId) {
        var catNew = new Category();
        catNew.setName(request.getName());
        if (request.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow();
            catNew.setParentCategory(parentCategory);
        }
        return categoryRepository.save(catNew);
    }
}
