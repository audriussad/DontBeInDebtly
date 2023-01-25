package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BudgetItemRepository budgetItemRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final CategoryMerger categoryMerger;

    public PortfolioService(ApplicationUserRepository applicationUserRepository,
                            BudgetItemRepository budgetItemRepository,
                            TransactionItemRepository transactionItemRepository,
                            CategoryMerger categoryMerger) {
        this.applicationUserRepository = applicationUserRepository;
        this.budgetItemRepository = budgetItemRepository;
        this.transactionItemRepository = transactionItemRepository;
        this.categoryMerger = categoryMerger;
    }

    public List<PortfolioItem> showPortfolio(Long userId, int year, int month) {
        var user = applicationUserRepository.findById(userId).orElseThrow();
        var budgetItems = budgetItemRepository.findByYearAndMonthAndUserId(year, month, userId);
        var transactions = transactionItemRepository.findByYearAndMonthAndUserId(year, month, userId);
        return categoryMerger.mergeCategoryDataToPortfolioItemList(budgetItems, transactions);
    }

}
