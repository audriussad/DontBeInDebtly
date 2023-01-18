package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

            for (BudgetItemEntity budgetItem: budgetItemEntities) {
                PortfolioItem portfolioItem = new PortfolioItem();
                Category category = budgetItem.getCategory();
                List<TransactionItemEntity> userTransactions = transactionItemRepository
                        .findByYearAndMonthAndCategoryAndUserId(year, month, category, userId);
                BigDecimal calculatedCategoryExpense = calculateCategoryExpense(userTransactions);

                portfolioItem.setCategoryId(category.getId());
                if (category.getParentCategory() != null) {
                    portfolioItem.setParentCategoryId(category.getParentCategory().getId());
                }

                portfolioItem.setPlannedAmount(budgetItem.getPlannedAmount());
                //portfolioItem.setPlannedAmount(budgetItemRepository.findByCategoryId(category.getId()).get().getPlannedAmount());
                portfolioItem.setActualAmount(calculatedCategoryExpense);
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
