package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {
    private final ApplicationUserRepository applicationUserRepository;

    private final CategoryMerger categoryMerger;

    public PortfolioService(ApplicationUserRepository applicationUserRepository,
                            CategoryMerger categoryMerger) {
        this.applicationUserRepository = applicationUserRepository;
        this.categoryMerger = categoryMerger;
    }

    public List<PortfolioItem> showPortfolio(Long userId, int year, int month) {
        var user = applicationUserRepository.findById(userId).orElseThrow();
        return categoryMerger.mergeCategoryDataToPortfolioItemList(year, month, userId);
    }

}
