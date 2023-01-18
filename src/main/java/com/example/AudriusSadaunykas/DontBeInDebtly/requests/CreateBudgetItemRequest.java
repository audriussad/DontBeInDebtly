package com.example.AudriusSadaunykas.DontBeInDebtly.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class CreateBudgetItemRequest {

    private Long id;

    private BigDecimal plannedAmount;

    private Long categoryId;
    private LocalDate date;
}
