package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.UserPrincipal;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.AudriusSadaunykas.DontBeInDebtly.services.TransactionItemService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/transactions")
public class TransactionItemController {

    private final TransactionItemService transactionItemService;

    @Autowired
    public TransactionItemController(TransactionItemService transactionItemService) {
        this.transactionItemService = transactionItemService;
    }

    @GetMapping("")
    public List<TransactionItemEntity> getTransactions(
            //@CurrentSecurityContext(expression = "authentication.name") String username
            @AuthenticationPrincipal Object user
            )
    {
        return transactionItemService.getTransactions(Long.valueOf(user.toString()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionItemEntity> get(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserPrincipal user) {
        try {
            TransactionItemEntity transactionItem = transactionItemService.getTransaction(id,
                    user.getUserId());
            return new ResponseEntity<TransactionItemEntity>(transactionItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TransactionItemEntity>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public TransactionItemEntity addNewTransaction(@RequestBody CreateTransactionItemRequest request,
                                                  // @CurrentSecurityContext(expression = "authentication.name") String username
                                                    @AuthenticationPrincipal UserPrincipal user) {
        return transactionItemService.saveTransaction(request, user.getUserId());
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('transaction:write')")
    public ResponseEntity<String> update(@RequestBody CreateTransactionItemRequest request,
                                         @AuthenticationPrincipal UserPrincipal user) {
        return transactionItemService.editTransaction(request, user.getUserId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('transaction:write')")
    public ResponseEntity<TransactionItemEntity> delete(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserPrincipal user) {
        try {
            TransactionItemEntity transactionItem = transactionItemService.getTransaction(id, user.getUserId());
            transactionItemService.deleteTransaction(id);
            return new ResponseEntity<TransactionItemEntity>(transactionItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TransactionItemEntity>(HttpStatus.NOT_FOUND);
        }
    }

}
