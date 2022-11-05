package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateTransactionItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<TransactionItemEntity> getTransactions() {
        return transactionItemService.getTransactions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<TransactionItemEntity> get(@PathVariable Long id) {
        try {
            TransactionItemEntity transactionItem = transactionItemService.getTransaction(id);
            return new ResponseEntity<TransactionItemEntity>(transactionItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TransactionItemEntity>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('transaction:write')")
    public TransactionItemEntity addNewTransaction(@RequestBody CreateTransactionItemRequest request) {
        return transactionItemService.saveTransaction(request);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('transaction:write')")
    public TransactionItemEntity update(@RequestBody CreateTransactionItemRequest request) {
        return transactionItemService.editTransaction(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('transaction:write')")
    public ResponseEntity<TransactionItemEntity> delete(@PathVariable Long id) {
        try {
            TransactionItemEntity transactionItem = transactionItemService.getTransaction(id);
            transactionItemService.deleteTransaction(id);
            return new ResponseEntity<TransactionItemEntity>(transactionItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TransactionItemEntity>(HttpStatus.NOT_FOUND);
        }
    }

}
