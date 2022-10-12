package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateBudgetItemRequest;
import com.example.AudriusSadaunykas.DontBeInDebtly.services.BudgetItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/budget-item")
public class BudgetItemController {
    private final BudgetItemService budgetItemService;

    @Autowired
    public BudgetItemController(BudgetItemService budgetItemService) {
        this.budgetItemService = budgetItemService;
    }
    private ModelMapper modelMapper;

    @GetMapping("")
    public List<BudgetItemEntity> getBudget() {
        return budgetItemService.getBudget();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetItemEntity> getBudgetItem(@PathVariable Long id) {
        try {
            BudgetItemEntity budgetItem = budgetItemService.getBudgetItem(id);
            return new ResponseEntity<BudgetItemEntity>(budgetItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<BudgetItemEntity>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public BudgetItemEntity saveBudgetItem(@RequestBody CreateBudgetItemRequest request) {
        return budgetItemService.saveBudgetItem(request);
    }

    @PutMapping("/")
    public BudgetItemEntity update(@RequestBody CreateBudgetItemRequest request) {
        return budgetItemService.editBudgetItem(request);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BudgetItemEntity> delete(@PathVariable Long id) {
        try {
            BudgetItemEntity budgetItem = budgetItemService.getBudgetItem(id);
            budgetItemService.deleteBudgetItem(id);
            return new ResponseEntity<BudgetItemEntity>(budgetItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<BudgetItemEntity>(HttpStatus.NOT_FOUND);
        }
    }
}
