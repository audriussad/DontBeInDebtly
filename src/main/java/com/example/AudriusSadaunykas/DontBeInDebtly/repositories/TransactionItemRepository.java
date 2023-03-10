package com.example.AudriusSadaunykas.DontBeInDebtly.repositories;

import com.example.AudriusSadaunykas.DontBeInDebtly.entities.Category;
import com.example.AudriusSadaunykas.DontBeInDebtly.entities.TransactionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItemEntity, Long> {
    List<TransactionItemEntity> findByUserId(Long id);

    Optional<TransactionItemEntity> findById(Long id);
    @Query("SELECT t FROM TransactionItemEntity t WHERE YEAR(t.date) = ?1 AND MONTH(t.date) = ?2 AND t.category = ?3 AND t.userId = ?4")
    List<TransactionItemEntity> findByYearAndMonthAndCategoryAndUserId(int year, int month, Category category, Long userIs);

    @Query("SELECT t FROM TransactionItemEntity t WHERE YEAR(t.date) = ?1 AND MONTH(t.date) = ?2 AND t.userId = ?3")
    List<TransactionItemEntity> findByYearAndMonthAndUserId(int year, int month, Long userIs);
}
