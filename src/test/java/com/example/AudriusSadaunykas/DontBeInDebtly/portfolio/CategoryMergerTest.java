package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryMergerTest {


    @Autowired
    TransactionItemRepository transactionItemRepository;
    @Autowired
    CategoryMerger categoryMerger;


    @Test
    @Disabled
    void mergeCategoryDataToPortfolioItemList() {

    }


    @Test
    @Disabled
    void getCategoriesAndExpenses() {
    }

    @Test
    void doesItCalculateCategoryExpenseCorrectly() {
        // given
        int year = 2022;
        int month = 12;
        Long userId = 1L;
        Category category = new Category(1L, "testCat", null);

        TransactionItemEntity entity1 = new TransactionItemEntity();
            entity1.setAmount(BigDecimal.valueOf(10.31));

        TransactionItemEntity entity2 = new TransactionItemEntity();
            entity2.setAmount(BigDecimal.valueOf(20));

        TransactionItemEntity entity3 = new TransactionItemEntity();
            entity3.setAmount(BigDecimal.valueOf(1.00));

        List<TransactionItemEntity> entityList = List.of(entity1, entity2, entity3);
        // when

        // then

        BigDecimal expectedSum = BigDecimal.valueOf(31.31);
        BigDecimal calculatedSum = CategoryMerger.calculateCategoryExpense(entityList);
        assertEquals(expectedSum, calculatedSum);
    }

    @Test
    public void testMergeCategoryDataToPortfolioItemList_validInput() {
        int year = 2022;
        int month = 2;
        Long userId = 1L;

        // mock the CategoryRepository and TransactionItemRepository objects
        CategoryRepository mockCategoryRepository = mock(CategoryRepository.class);
        TransactionItemRepository mockTransactionItemRepository = mock(TransactionItemRepository.class);
        BudgetItemRepository mockBudgetItemRepository = mock(BudgetItemRepository.class);

        // set up the mock CategoryRepository to return a list of categories
        List<Category> listCategories = new ArrayList<>();
        listCategories.add(new Category(1L, "Category 1", null));
        listCategories.add(new Category(2L, "Category 2", null));
        when(mockCategoryRepository.findAll()).thenReturn(listCategories);

        // set up the mock TransactionItemRepository to return a list of transactions for a given category and user
        List<TransactionItemEntity> listTransactions = new ArrayList<>();
        listTransactions.add(new TransactionItemEntity(1L, new BigDecimal("100.00"), listCategories.get(0), year, month, 1, userId));
        listTransactions.add(new TransactionItemEntity(2L, new BigDecimal("50.00"), listCategories.get(0), year, month, 1, userId));
        when(mockTransactionItemRepository.findByYearAndMonthAndCategoryAndUserId(year, month, listCategories.get(0), userId)).thenReturn(listTransactions);

        // set up the mock BudgetItemRepository to return a budget item for a given category
        when(mockTransactionItemRepository.findByYearAndMonthAndCategoryAndUserId(year, month, listCategories.get(0), userId)).thenReturn(listTransactions);

        // create an instance of the CategoryMerger class and call the mergeCategoryDataToPortfolioItemList method
        CategoryMerger categoryMerger = new CategoryMerger(mockCategoryRepository, mockBudgetItemRepository, mockTransactionItemRepository);
        List<PortfolioItem> portfolioItemList = categoryMerger.mergeCategoryDataToPortfolioItemList(year, month, userId);

        // assert that the returned list of PortfolioItem objects is not null and has a size of 2
        assertNotNull(portfolioItemList);
        assertEquals(2, portfolioItemList.size());
    }

}