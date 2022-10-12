package com.example.AudriusSadaunykas.DontBeInDebtly.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor

public class CreateBudgetItemRequest {

    private Long id;

    private int year;

    private int month;

    private BigDecimal plannedAmount;

    private Long categoryId;
}
