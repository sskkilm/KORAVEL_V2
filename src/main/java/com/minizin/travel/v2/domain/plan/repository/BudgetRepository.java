package com.minizin.travel.v2.domain.plan.repository;

import com.minizin.travel.v2.domain.plan.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("select b from Budget b " +
            "join fetch b.place pl " +
            "join fetch pl.schedule s " +
            "join fetch s.plan p " +
            "where b.id = :id")
    Optional<Budget> findByIdWithPlaceAndScheduleAndPlan(@Param("id") Long id);
}
