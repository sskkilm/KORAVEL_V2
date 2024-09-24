package com.minizin.travel.v2.domain.plan.repository;

import com.minizin.travel.v2.domain.plan.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
