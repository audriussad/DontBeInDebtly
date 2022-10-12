package com.example.AudriusSadaunykas.DontBeInDebtly.dto;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDTO {
    private Long id;
    private Category category;
    private int year;
    private int month;
    private BigDecimal plannedAmount;
    private BigDecimal actualAmount;
}
