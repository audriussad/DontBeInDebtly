package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMerger {


    public List<PortfolioItem> mergeCategoryDataToPortfolioItemList(List<BudgetItemEntity> budgetItems, List<TransactionItemEntity> transactions) {

            List<PortfolioItem> portfolioItemList = getChildCategoryPortfolioItems(budgetItems, transactions);
            Set<Category> parentCategories = getParentCategorySet(budgetItems);

            for (Category parent: parentCategories) {
                PortfolioItem portfolioItem = new PortfolioItem();
                portfolioItem.setCategoryId(parent.getId());
                portfolioItem.setPlannedAmount(getPlanedAmountForParentClass(parent, portfolioItemList));
                portfolioItem.setActualAmount(getActualAmountForParentClass(parent, portfolioItemList));
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

    private List<PortfolioItem> getChildCategoryPortfolioItems(List<BudgetItemEntity> budgetItems, List<TransactionItemEntity> transactions) {
        List<PortfolioItem> portfolioItemList = new ArrayList<>();

        for (BudgetItemEntity budgetItem: budgetItems) {
            Category category = budgetItem.getCategory();
            List<TransactionItemEntity> userTransactions = transactions.stream()
                    .filter(transactionItemEntity -> transactionItemEntity.getCategory().equals(category))
                    .collect(Collectors.toList());
            BigDecimal calculatedCategoryExpense = calculateCategoryExpense(userTransactions);

            PortfolioItem portfolioItem = new PortfolioItem();
            portfolioItem.setCategoryId(category.getId());
            portfolioItem.setParentCategoryId(category.getParentCategory().getId());
            portfolioItem.setPlannedAmount(budgetItem.getPlannedAmount());
            portfolioItem.setActualAmount(calculatedCategoryExpense);
            portfolioItemList.add(portfolioItem);
        }
        return portfolioItemList;
    }

    private Set<Category> getParentCategorySet(List<BudgetItemEntity> budgetItems) {
        Set<Category> parentCategories = new HashSet<>();

        for (BudgetItemEntity budgetItem: budgetItems) {
            Category category = budgetItem.getCategory();
            parentCategories.add(category.getParentCategory());
        }
        return parentCategories;
    }

    private BigDecimal getPlanedAmountForParentClass (Category parent, List<PortfolioItem> portfolioItemList) {
        BigDecimal plannedAmount = BigDecimal.ZERO;
        for (PortfolioItem portfolioItem1: portfolioItemList) {
            if (parent.getId().equals(portfolioItem1.getParentCategoryId())) {
                plannedAmount = plannedAmount.add(portfolioItem1.getPlannedAmount());
            }
        }
        return plannedAmount;
    }

    private BigDecimal getActualAmountForParentClass (Category parent, List<PortfolioItem> portfolioItemList) {
        BigDecimal actualAmount = BigDecimal.ZERO;
        for (PortfolioItem portfolioItem1: portfolioItemList) {
            if (parent.getId().equals(portfolioItem1.getParentCategoryId())) {
                actualAmount = actualAmount.add(portfolioItem1.getActualAmount());
            }
        }
        return actualAmount;
    }


}
