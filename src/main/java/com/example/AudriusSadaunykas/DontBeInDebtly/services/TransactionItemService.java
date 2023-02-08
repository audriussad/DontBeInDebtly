package com.example.AudriusSadaunykas.DontBeInDebtly.services;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.Users.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.CategoryRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.AudriusSadaunykas.DontBeInDebtly.repositories.TransactionItemRepository;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionItemService {
    private final TransactionItemRepository transactionItemRepository;
    private final CategoryRepository categoryRepository;


    public List<TransactionItemEntity> getTransactions(Long userId) {
        return transactionItemRepository.findByUserId(userId);
    }

    public TransactionItemEntity saveTransaction(CreateTransactionItemRequest request, Long userId) {
        var entity = new TransactionItemEntity();
        entity.setAmount(request.getAmount());
        entity.setDate(request.getDate());
        entity.setCreatedAt(Instant.now());
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        entity.setCategory(category);
        entity.setUserId(userId);
        return transactionItemRepository.save(entity);
    }

    public TransactionItemEntity editTransaction(CreateTransactionItemRequest request, Long userId) {
        TransactionItemEntity entity = transactionItemRepository.findById(request.getId()).orElseThrow();
        if (isAuthorized(entity.getUserId(), userId)) {


            if (request.getAmount() != null) {
                entity.setAmount(request.getAmount());
            }
            if (request.getCategoryId() != null) {
                var category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
                entity.setCategory(category);
            }
            transactionItemRepository.save(entity);
            return entity;
        } else {
            return new TransactionItemEntity();
        }
    }

    public Optional<TransactionItemEntity> getTransaction(Long id, Long userId) {
        try {
            var entity = transactionItemRepository.findById(id);
            if (isAuthorized(id, userId)) {
                return entity;
            }
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void deleteTransaction(Long id, Long userId) {
        if (isAuthorized(id, userId)) {
            transactionItemRepository.deleteById(id);
        }
    }

    public boolean isAuthorized(Long entityId, Long userId) {
        var entity = transactionItemRepository.findById(entityId).orElseThrow();
        if (entity.getUserId() == userId) {
            return true;
        } else {
            return false;
        }
    }



}