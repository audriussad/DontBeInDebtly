package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionItemService {
    private final TransactionItemRepository transactionItemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TransactionItemService(TransactionItemRepository transactionItemRepository, CategoryRepository categoryRepository) {
        this.transactionItemRepository = transactionItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TransactionItemEntity> getTransactions() {
        return transactionItemRepository.findAll();
    }

    public TransactionItemEntity saveTransaction(CreateTransactionItemRequest request) {
        var entity = new TransactionItemEntity();
        entity.setAmount(request.getAmount());
        entity.setYear(request.getYear());
        entity.setMonth(request.getMonth());
        entity.setDay(request.getDay());
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        entity.setCategory(category);
        return transactionItemRepository.save(entity);
    }

    public TransactionItemEntity editTransaction(CreateTransactionItemRequest request) {
        TransactionItemEntity entity = transactionItemRepository.findById(request.getId()).orElseThrow();
        if (request.getYear() != 0) {
            entity.setYear(request.getYear());
        }
        if (request.getMonth() != 0) {
            entity.setMonth(request.getMonth());
        }
        if (request.getDay() != 0) {
            entity.setDay(request.getDay());
        }
        if (request.getAmount() != null) {
            entity.setAmount(request.getAmount());
        }
        if (request.getCategoryId() != null) {
            var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
            entity.setCategory(category);
        }
        return transactionItemRepository.save(entity);
    }

    public TransactionItemEntity getTransaction(Long id) {
        return transactionItemRepository.findById(id).get();
    }

    public void deleteTransaction(Long id) {
        transactionItemRepository.deleteById(id);
    }

    public BigDecimal sumOfMonthlyTransactions(Integer year, Integer month, Category category){
        List<TransactionItemEntity> monthTransactions = transactionItemRepository.findByYearAndMonthAndCategory(year, month, category);
        BigDecimal sumTransactions = BigDecimal.valueOf(0.00);
        for (TransactionItemEntity transaction: monthTransactions) {
                sumTransactions = sumTransactions.add(transaction.getAmount());
        }
        return sumTransactions;
    }

}