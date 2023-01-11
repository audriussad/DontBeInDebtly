package com.example.AudriusSadaunykas.DontBeInDebtly.repositories;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItemEntity, Long> {
    List<TransactionItemEntity> findByYearAndMonthAndCategory(Integer year, Integer month, Category category);
    List<TransactionItemEntity> findByUserId(Long id);
    List<TransactionItemEntity> findByYearAndMonthAndCategoryAndUserId(Integer year, Integer month, Category category, Long userIs);
}
