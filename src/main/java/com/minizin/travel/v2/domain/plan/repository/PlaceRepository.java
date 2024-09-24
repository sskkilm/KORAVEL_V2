package com.minizin.travel.v2.domain.plan.repository;

import com.minizin.travel.v2.domain.plan.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("select p from Place p " +
            "join fetch p.schedule s " +
            "join fetch s.plan pl " +
            "where p.id = :id")
    Optional<Place> findByIdWithScheduleAndPlan(@Param("id") Long id);
}
