package com.example.AudriusSadaunykas.DontBeInDebtly.entities;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transactions")
public class TransactionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @ManyToOne
    private Category category;
    //TODO: Change to proper date class
    private int year;
    private int month;
    private int day;
//    @ManyToOne
//    private ApplicationUser user;
    private Long userId;

}


