package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionItemService {
    private final TransactionItemRepository transactionItemRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public TransactionItemService(TransactionItemRepository transactionItemRepository, CategoryRepository categoryRepository, ApplicationUserRepository applicationUserRepository) {
        this.transactionItemRepository = transactionItemRepository;
        this.categoryRepository = categoryRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    public List<TransactionItemEntity> getTransactions(Long userId) {
        var user = applicationUserRepository.findById(userId).orElseThrow();
        return transactionItemRepository.findByUserId(user.getId());
    }

    public TransactionItemEntity saveTransaction(CreateTransactionItemRequest request, Long userId) {
        var entity = new TransactionItemEntity();
        entity.setAmount(request.getAmount());
        entity.setYear(request.getYear());
        entity.setMonth(request.getMonth());
        entity.setDay(request.getDay());
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        entity.setCategory(category);
        var user = applicationUserRepository.findById(userId).orElseThrow();
        entity.setUserId(user.getId());
        return transactionItemRepository.save(entity);
    }

    public ResponseEntity<String> editTransaction(CreateTransactionItemRequest request, Long userId) {
        TransactionItemEntity entity = transactionItemRepository.findById(request.getId()).orElseThrow();
        if (isAuthorized(entity.getUserId(), userId)) {


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
            transactionItemRepository.save(entity);
            return new ResponseEntity<>("Edited", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public  TransactionItemEntity getTransaction(Long id, Long userId) {
        var entity = transactionItemRepository.findById(id).get();
        if (isAuthorized(entity.getUserId(), userId)) {
            return entity;
        }
        return new TransactionItemEntity();
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

    public boolean isAuthorized(Long entityId, Long userId) {
        TransactionItemEntity entity = transactionItemRepository.findById(entityId).orElseThrow();
        if (entity.getUserId() == userId) {
            return true;
        } else {
            return false;
        }
    }



}