package com.example.AudriusSadaunykas.DontBeInDebtly.services;

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

    @Autowired
    public BudgetItemService(BudgetItemRepository budgetItemRepository, CategoryRepository categoryRepository) {
        this.budgetItemRepository = budgetItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BudgetItemEntity> getBudget() {
        return budgetItemRepository.findAll();
    }

    public BudgetItemEntity saveBudgetItem(CreateBudgetItemRequest request) {
        var entity = new BudgetItemEntity();
        entity.setYear(request.getYear());
        entity.setMonth(request.getMonth());
        entity.setPlannedAmount(request.getPlannedAmount());
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        entity.setCategory(category);
        return budgetItemRepository.save(entity);
    }

    public BudgetItemEntity editBudgetItem(CreateBudgetItemRequest request) {
        BudgetItemEntity entity = budgetItemRepository.findById(request.getId()).orElseThrow();
        if (request.getYear() != 0) {
            entity.setYear(request.getYear());
        }
        if (request.getMonth() != 0) {
            entity.setMonth(request.getMonth());
        }
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
