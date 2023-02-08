package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.UserPrincipal;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.CreateBudgetItemRequest;
import com.example.AudriusSadaunykas.DontBeInDebtly.services.BudgetItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budget-item")
public class BudgetItemController {
    private final BudgetItemService budgetItemService;

    @GetMapping
    public List<BudgetItemEntity> getBudget(@AuthenticationPrincipal UserPrincipal user) {
        return budgetItemService.getBudget(user.getUserId());
    }

    @GetMapping("/{id}")
    public Optional<BudgetItemEntity> getBudgetItem(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserPrincipal user) {
            return budgetItemService.getBudgetItem(id, user.getUserId());

    }

    @PostMapping
    public BudgetItemEntity saveBudgetItem(@RequestBody CreateBudgetItemRequest request,
                                           @AuthenticationPrincipal UserPrincipal user) throws Exception {
        return budgetItemService.saveBudgetItem(request, user.getUserId());
    }

    @PutMapping
    public BudgetItemEntity update(@RequestBody CreateBudgetItemRequest request) {
        return budgetItemService.editBudgetItem(request);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal user) {
        budgetItemService.deleteBudgetItem(id, user.getUserId());
    }
}
