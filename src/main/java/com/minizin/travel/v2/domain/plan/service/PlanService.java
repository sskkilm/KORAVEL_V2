package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.dto.PlanDeleteResponseDto;
import com.minizin.travel.v2.domain.plan.dto.PlanUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.repository.PlanRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public PlanCreateDto.Response createPlan(PlanCreateDto.Request request) {

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("startDate cannot be after than endDate");
        }

        return PlanCreateDto.Response.toDto(
                planRepository.save(
                        PlanCreateDto.Request.toEntity(request, UserEntity.builder().id(1L).build())
                )
        );
    }

    @Transactional
    public PlanUpdateDto.Response updatePlan(Long planId, PlanUpdateDto.Request request) {

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("startDate cannot be after than endDate");
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("plan not found"));

        plan.update(
                request.title(), request.thema(), request.startDate(),
                request.endDate(), request.visibility(), request.numberOfMembers()
        );

        return PlanUpdateDto.Response.toDto(plan);
    }

    @Transactional
    public PlanDeleteResponseDto deletePlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("plan not found"));

        planRepository.delete(plan);

        return new PlanDeleteResponseDto("success");
    }
}
