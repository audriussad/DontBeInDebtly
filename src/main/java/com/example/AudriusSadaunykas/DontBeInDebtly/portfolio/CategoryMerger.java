package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        List<Category> listCategories = categoryRepository.findAll();

        for (Category category: listCategories) {
            PortfolioItem portfolioItem = new PortfolioItem();
            List<TransactionItemEntity> userTransactions = transactionItemRepository
                    .findByYearAndMonthAndCategoryAndUserId(year, month, category, userId);
            BigDecimal calculatedCategoryExpense = calculateCategoryExpense(userTransactions);

            if (calculatedCategoryExpense.equals(BigDecimal.valueOf(0.00))) {
                continue;
            }
            portfolioItem.setCategoryId(category.getId());
            if (category.getParentCategory() != null) {
                portfolioItem.setParentCategoryId(category.getParentCategory().getId());
            }

            portfolioItem.setPlannedAmount(budgetItemRepository.findByCategoryId(category.getId()).get().getPlannedAmount());
            portfolioItem.setActualAmount(calculatedCategoryExpense);
            portfolioItemList.add(portfolioItem);
        }
        return portfolioItemList;
    }

    public HashMap<Long, BigDecimal> getCategoriesAndExpenses(int year, int month, Long userId){
        List<Category> listCategories = categoryRepository.findAll();
        HashMap<Long, BigDecimal> categoriesAndExpenses = new HashMap<>();
        for (Category category: listCategories) {
            List<TransactionItemEntity> userTransactions = transactionItemRepository
                    .findByYearAndMonthAndCategoryAndUserId(year, month, category, userId);
            categoriesAndExpenses.put(category.getId(), calculateCategoryExpense(userTransactions));
        }
        return categoriesAndExpenses;
    }

    static BigDecimal calculateCategoryExpense(@NotNull List<TransactionItemEntity> userTransactions) {
        BigDecimal sumOfExpenses = userTransactions.stream()
                .map(TransactionItemEntity::getAmount)
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
        return sumOfExpenses;
    }

}
