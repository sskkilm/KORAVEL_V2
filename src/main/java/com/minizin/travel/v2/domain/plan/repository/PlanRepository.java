package com.minizin.travel.v2.domain.plan.repository;

import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByUser(UserEntity user);
}
