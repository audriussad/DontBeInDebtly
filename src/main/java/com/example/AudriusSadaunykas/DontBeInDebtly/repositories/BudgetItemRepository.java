package com.example.AudriusSadaunykas.DontBeInDebtly.repositories;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.BudgetItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItemEntity, Long> {

    Optional<BudgetItemEntity> findByCategoryId(Long categoryId);

    Optional<BudgetItemEntity> findById(Long id);

    @Query("SELECT t FROM BudgetItemEntity t WHERE YEAR(t.date) = ?1 AND MONTH(t.date) = ?2 AND t.userId = ?3")
    List<BudgetItemEntity> findByYearAndMonthAndUserId(int year, int month, Long userId);
}
