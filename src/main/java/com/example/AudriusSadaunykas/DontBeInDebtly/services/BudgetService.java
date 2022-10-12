package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.dto.BudgetDTO;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetItemRepository budgetItemRepository;
    private TransactionItemService transactionItemService;
    private final CategoryRepository categoryRepository;

    public BudgetService(BudgetItemRepository budgetItemRepository, TransactionItemService transactionItemService, CategoryRepository  categoryRepository) {
        this.budgetItemRepository = budgetItemRepository;
        this.transactionItemService = transactionItemService;
        this.categoryRepository = categoryRepository;
    }

    public List<BudgetDTO> getMonthBudget(Integer year, Integer month) {
        return budgetItemRepository.findByYearAndMonth(year, month)
                .stream()
                .map(this::createDto)
                .collect(Collectors.toList());
    }

    public BudgetDTO createDto(BudgetItemEntity budgetItemEntity) {
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setId(budgetItemEntity.getId());
        budgetDTO.setCategory(budgetItemEntity.getCategory());
        budgetDTO.setYear(budgetItemEntity.getYear());
        budgetDTO.setMonth(budgetItemEntity.getMonth());
        budgetDTO.setPlannedAmount(budgetItemEntity.getPlannedAmount());
        budgetDTO.setActualAmount(this.transactionItemService.sumOfMonthlyTransactions(budgetDTO.getYear(),
                budgetDTO.getMonth(), budgetDTO.getCategory()));
        return budgetDTO;
    }


}
