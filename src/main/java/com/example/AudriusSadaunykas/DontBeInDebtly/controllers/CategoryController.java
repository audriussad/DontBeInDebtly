package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.UserPrincipal;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateCategoryRequest;
import com.example.AudriusSadaunykas.DontBeInDebtly.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> showUsersCategories(@AuthenticationPrincipal UserPrincipal user) {
        return categoryService.showUsersCategories(user.getUserId());
    }
    @PostMapping
    public Category addNewCategory(@RequestBody CreateCategoryRequest request,
                                      @AuthenticationPrincipal UserPrincipal user) throws Exception {
        return categoryService.saveNewCategory(request, user.getUserId());
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id,
                               @AuthenticationPrincipal UserPrincipal user) {
        categoryService.deleteCategory(id, user.getUserId());
    }
}
