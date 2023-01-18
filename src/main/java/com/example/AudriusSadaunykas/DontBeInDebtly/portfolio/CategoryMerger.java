package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CategoryMerger {
    private final CategoryRepository categoryRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final BudgetItemRepository budgetItemRepository;

    public CategoryMerger(CategoryRepository categoryRepository,
                          BudgetItemRepository budgetItemRepository,
                          TransactionItemRepository transactionItemRepository) {
        this.categoryRepository = categoryRepository;
        this.budgetItemRepository = budgetItemRepository;
        this.transactionItemRepository = transactionItemRepository;
    }

    public List<PortfolioItem> mergeCategoryDataToPortfolioItemList(int year, int month, Long userId) {
            List<PortfolioItem> portfolioItemList = new ArrayList<>();
            List<BudgetItemEntity> budgetItemEntities = budgetItemRepository.findByYearAndMonthAndUserId(year, month, userId);
            Set<Category> parentCategories = new HashSet<>();

            for (BudgetItemEntity budgetItem: budgetItemEntities) {
                Category category = budgetItem.getCategory();
                List<TransactionItemEntity> userTransactions = transactionItemRepository
                        .findByYearAndMonthAndCategoryAndUserId(year, month, category, userId);
                BigDecimal calculatedCategoryExpense = calculateCategoryExpense(userTransactions);

                PortfolioItem portfolioItem = new PortfolioItem();
                portfolioItem.setCategoryId(category.getId());
                portfolioItem.setParentCategoryId(category.getParentCategory().getId());

                parentCategories.add(category.getParentCategory());

                portfolioItem.setPlannedAmount(budgetItem.getPlannedAmount());
                portfolioItem.setActualAmount(calculatedCategoryExpense);
                portfolioItemList.add(portfolioItem);
            }

            for (Category parent: parentCategories) {
                PortfolioItem portfolioItem = new PortfolioItem();
                portfolioItem.setCategoryId(parent.getId());

                BigDecimal plannedAmount = BigDecimal.ZERO;
                BigDecimal actualAmount = BigDecimal.ZERO;

                for (PortfolioItem portfolioItem1: portfolioItemList) {
                    if (parent.getId().equals(portfolioItem1.getParentCategoryId())) {
                        plannedAmount = plannedAmount.add(portfolioItem1.getPlannedAmount());
                        actualAmount = actualAmount.add(portfolioItem1.getActualAmount());

                    }
                }
                portfolioItem.setPlannedAmount(plannedAmount);
                portfolioItem.setActualAmount(actualAmount);
                portfolioItemList.add(portfolioItem);

            }
            return portfolioItemList;
    }

    static BigDecimal calculateCategoryExpense(@NotNull List<TransactionItemEntity> userTransactions) {
        BigDecimal sumOfExpenses = userTransactions.stream()
                .map(TransactionItemEntity::getAmount)
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
        return sumOfExpenses;
    }


}
