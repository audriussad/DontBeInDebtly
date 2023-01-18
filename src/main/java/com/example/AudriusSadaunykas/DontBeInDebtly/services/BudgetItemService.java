package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateBudgetItemRequest;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetItemService {
    private final BudgetItemRepository budgetItemRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public BudgetItemService(BudgetItemRepository budgetItemRepository, CategoryRepository categoryRepository, ApplicationUserRepository applicationUserRepository) {
        this.budgetItemRepository = budgetItemRepository;
        this.categoryRepository = categoryRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    public List<BudgetItemEntity> getBudget() {
        return budgetItemRepository.findAll();
    }

    public BudgetItemEntity saveBudgetItem(CreateBudgetItemRequest request, Long userId) throws Exception {
        var entity = new BudgetItemEntity();
        entity.setPlannedAmount(request.getPlannedAmount());
        entity.setDate(request.getDate());
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        if (category.getParentCategory() == null) {
            throw new Exception("Cannot add budget to parent categories");
        }
        entity.setCategory(category);
        var user = applicationUserRepository.findById(userId).orElseThrow();
        entity.setUserId(user.getId());
        return budgetItemRepository.save(entity);
    }

    public BudgetItemEntity editBudgetItem(CreateBudgetItemRequest request) {
        BudgetItemEntity entity = budgetItemRepository.findById(request.getId()).orElseThrow();
        if (request.getPlannedAmount() != null) {
            entity.setPlannedAmount(request.getPlannedAmount());
        }
        if (request.getCategoryId() != null) {
            var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
            entity.setCategory(category);
        }
        return budgetItemRepository.save(entity);
    }

    public BudgetItemEntity getBudgetItem(Long id) {
        return budgetItemRepository.findById(id).get();
    }

    public void deleteBudgetItem(Long id) {
        budgetItemRepository.deleteById(id);
    }
}
