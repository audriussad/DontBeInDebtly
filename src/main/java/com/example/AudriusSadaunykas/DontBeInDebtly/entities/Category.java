package com.example.AudriusSadaunykas.DontBeInDebtly.entities;

import com.example.AudriusSadaunykas.DontBeInDebtly.services.BudgetItemService;
import com.example.AudriusSadaunykas.DontBeInDebtly.services.BudgetService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    @ManyToOne
    private Category parentCategory;


    public Category(Long id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
        this.parentCategory = null;
    }
}
