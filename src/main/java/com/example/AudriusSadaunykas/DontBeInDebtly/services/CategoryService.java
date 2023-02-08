package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveNewCategory(CreateCategoryRequest request, long userId) {
        var catNew = new Category();
        catNew.setName(request.getName());
        catNew.setUserId(userId);
        if (request.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(request.getParentId()).orElseThrow();
            catNew.setParentCategory(parentCategory);

        }
        return categoryRepository.save(catNew);
    }

    public List<Category> showUsersCategories(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public void deleteCategory(Long categoryId, Long userId) {
        if (isAuthenticated(categoryId, userId)) {
            categoryRepository.deleteById(categoryId);
        }
    }

    private boolean isAuthenticated(Long entityId, Long userId) {
        var entity = categoryRepository.findById(entityId).orElseThrow();
        if (entity.getUserId() == userId) {
            return true;
        }
        return false;
    }
}
