package com.example.AudriusSadaunykas.DontBeInDebtly.requests;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionItemRequest {
    private Long id;
    private BigDecimal amount;
    private int year;
    private int month;
    private int day;
    private Long categoryId;
}
