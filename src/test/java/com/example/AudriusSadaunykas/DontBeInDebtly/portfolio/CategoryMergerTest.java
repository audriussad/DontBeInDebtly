package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.BudgetItemRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryMergerTest {


    @Mock
    private TransactionItemRepository transactionItemRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BudgetItemRepository budgetItemRepository;
    @InjectMocks
    private CategoryMerger categoryMerger;



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

        // then

        BigDecimal expectedSum = BigDecimal.valueOf(31.31);
        BigDecimal calculatedSum = CategoryMerger.calculateCategoryExpense(entityList);
        assertEquals(expectedSum, calculatedSum);
    }

    @Test
    @DisplayName("Tests if CategoryMerger adds amounts correctly")
    public void testMergeCategoryDataToPortfolioItemList_validInput() {
        int year = 2023;
        int month = 1;
        Long userId = 1L;

        // Fake budgetItem
        BudgetItemEntity budgetItem = new BudgetItemEntity();
        budgetItem.setPlannedAmount(BigDecimal.valueOf(100.00));
        budgetItem.setDate(LocalDate.of(year,month,01));
        budgetItem.setUserId(userId);
        Category categoryParent = new Category();
        categoryParent.setId(1L);
        Category category = new Category();
        category.setId(2L);
        category.setParentCategory(categoryParent);
        budgetItem.setCategory(category);


        // Set up for budgetItemRepository to return fake budgetItem
        List<BudgetItemEntity> budgetItemEntities = new ArrayList<>();
        budgetItemEntities.add(budgetItem);
        when(budgetItemRepository.findByYearAndMonthAndUserId(year, month, userId)).thenReturn(budgetItemEntities);

        // Set up for transactionItemRepository to return fake transaction
        List<TransactionItemEntity> transactionItemEntities = new ArrayList<>();
        TransactionItemEntity transaction = new TransactionItemEntity();
        transaction.setAmount(BigDecimal.valueOf(50.00));
        transaction.setDate(LocalDate.of(year, month, 01));
        transaction.setCategory(category);
        transaction.setUserId(userId);
        transactionItemEntities.add(transaction);

        TransactionItemEntity transaction2 = new TransactionItemEntity();
        transaction2.setAmount(BigDecimal.valueOf(20.00));
        transaction2.setDate(LocalDate.of(year, month, 01));
        transaction2.setCategory(category);
        transaction2.setUserId(userId);
        transactionItemEntities.add(transaction2);


        when(transactionItemRepository.findByYearAndMonthAndCategoryAndUserId(year, month, category, userId))
                .thenReturn(transactionItemEntities);


        List<PortfolioItem> portfolioItemList = categoryMerger.mergeCategoryDataToPortfolioItemList(year, month, userId);



        assertNotNull(portfolioItemList);
        assertEquals(2, portfolioItemList.size());
        assertEquals(BigDecimal.valueOf(100.00), portfolioItemList.get(0).getPlannedAmount());
        assertEquals(BigDecimal.valueOf(70.00), portfolioItemList.get(0).getActualAmount());
    }

}