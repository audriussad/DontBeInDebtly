package com.example.AudriusSadaunykas.DontBeInDebtly.requests;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private Long id;
    private String name;
    private Long parentId;
}
