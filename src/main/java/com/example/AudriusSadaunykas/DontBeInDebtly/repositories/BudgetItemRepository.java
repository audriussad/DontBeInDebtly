package com.example.AudriusSadaunykas.DontBeInDebtly.repositories;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItemEntity, Long> {
    Optional<BudgetItemEntity> findByCategoryName(String categoryName);
    Optional<BudgetItemEntity> findByCategoryId(Long categoryId);
    Optional<BudgetItemEntity> findByYearAndMonthAndCategoryName(Integer year, Integer month, String categoryName);
    List<BudgetItemEntity> findByYear(Integer year);
    List<BudgetItemEntity> findByMonth(Integer month);
    List<BudgetItemEntity> findByYearAndMonth(Integer year, Integer month);
    Optional<BudgetItemEntity> findById(Long id);
}
