package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.Users.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateBudgetItemRequest;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetItemService {
    private final BudgetItemRepository budgetItemRepository;
    private final CategoryRepository categoryRepository;


    public List<BudgetItemEntity> getBudget(Long userId) {
        return budgetItemRepository.findByUserId(userId);
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
        entity.setUserId(userId);
        return budgetItemRepository.save(entity);
    }

    public BudgetItemEntity editBudgetItem(CreateBudgetItemRequest request) {
        BudgetItemEntity entity = budgetItemRepository.findById(request.getId()).orElseThrow();
        if (request.getPlannedAmount() != null) {
            entity.setPlannedAmount(request.getPlannedAmount());
        }
        if (request.getDate() != null) {
            entity.setDate(request.getDate());
        }
        if (request.getCategoryId() != null) {
            var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
            entity.setCategory(category);
        }
        return budgetItemRepository.save(entity);
    }

    public Optional<BudgetItemEntity> getBudgetItem(Long id, Long userId) {
        try {
            var entity = budgetItemRepository.findById(id);
            if (isAuthenticated(id, userId)) {
                return entity;
            }
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void deleteBudgetItem(Long id, Long userId) {
        if (isAuthenticated(id, userId))
        budgetItemRepository.deleteById(id);
    }

    private boolean isAuthenticated(Long entityId, Long userId) {
        var entity = budgetItemRepository.findById(entityId).orElseThrow();
        if (entity.getUserId() == userId) {
            return true;
        }
        return false;
    }
}

