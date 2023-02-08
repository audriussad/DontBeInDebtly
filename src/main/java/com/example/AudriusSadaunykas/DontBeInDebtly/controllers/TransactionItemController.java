package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.UserPrincipal;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.AudriusSadaunykas.DontBeInDebtly.services.TransactionItemService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionItemController {

    private final TransactionItemService transactionItemService;

    @GetMapping
    public List<TransactionItemEntity> getTransactions(
            @AuthenticationPrincipal UserPrincipal user
            )
    {
        return transactionItemService.getTransactions(user.getUserId());
    }

    @GetMapping("/{id}")
    public Optional<TransactionItemEntity> get(@PathVariable Long id,
                                               @AuthenticationPrincipal UserPrincipal user) {
        return transactionItemService.getTransaction(id, user.getUserId());
    }

    @PostMapping
    public TransactionItemEntity addNewTransaction(@RequestBody CreateTransactionItemRequest request,
                                                    @AuthenticationPrincipal UserPrincipal user) {
        return transactionItemService.saveTransaction(request, user.getUserId());
    }

    @PutMapping
    public TransactionItemEntity update(@RequestBody CreateTransactionItemRequest request,
                                         @AuthenticationPrincipal UserPrincipal user) {
        return transactionItemService.editTransaction(request, user.getUserId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                                        @AuthenticationPrincipal UserPrincipal user) {
        transactionItemService.deleteTransaction(id, user.getUserId());
    }

}
