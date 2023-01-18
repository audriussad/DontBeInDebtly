package com.example.AudriusSadaunykas.DontBeInDebtly.entities;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @ManyToOne
    private Category category;
    private LocalDate date;
    private Instant createdAt;
    private Long userId;

    public void setDate(LocalDate date) {
        this.date = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
    }


}




