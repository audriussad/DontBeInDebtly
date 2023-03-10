package com.example.AudriusSadaunykas.DontBeInDebtly.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "budget")
public class BudgetItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long userId;
    private BigDecimal plannedAmount;
    @ManyToOne
    private Category category;

}
