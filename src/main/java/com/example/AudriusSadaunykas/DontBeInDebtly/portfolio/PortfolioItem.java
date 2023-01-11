package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioItem {
    private Long categoryId;
    private Long parentCategoryId;
    private BigDecimal plannedAmount;
    private BigDecimal actualAmount;
}
