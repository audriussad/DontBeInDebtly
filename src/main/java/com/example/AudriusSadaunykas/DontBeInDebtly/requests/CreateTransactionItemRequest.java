package com.example.AudriusSadaunykas.DontBeInDebtly.requests;


import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class CreateTransactionItemRequest {
    private Long id;
    private BigDecimal amount;
    private Long categoryId;
    private LocalDate date;
}
