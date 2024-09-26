package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.dto.PlanDeleteDto;
import com.minizin.travel.v2.domain.plan.dto.PlanDto;
import com.minizin.travel.v2.domain.plan.dto.PlanUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.repository.PlanRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public PlanCreateDto.Response createPlan(PlanCreateDto.Request request, UserEntity user) {

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("startDate cannot be after than endDate");
        }

        return PlanCreateDto.Response.toDto(
                planRepository.save(
                        PlanCreateDto.Request.toEntity(request, user)
                )
        );
    }

    @Transactional
    public PlanUpdateDto.Response updatePlan(Long planId, PlanUpdateDto.Request request, UserEntity user) {

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("startDate cannot be after than endDate");
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("plan not found"));

        if (!Objects.equals(plan.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("user plan unmatched");
        }

        plan.update(
                request.title(), request.thema(), request.startDate(),
                request.endDate(), request.visibility(), request.numberOfMembers()
        );

        return PlanUpdateDto.Response.toDto(plan);
    }

    @Transactional
    public PlanDeleteDto deletePlan(Long planId, UserEntity user) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("plan not found"));

        if (!Objects.equals(plan.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("user plan unmatched");
        }

        planRepository.delete(plan);

        return new PlanDeleteDto("success");
    }

    @Transactional(readOnly = true)
    public List<PlanDto> getMyPlanList(UserEntity user) {

        return planRepository.findAllByUserOrderById(user).stream().map(PlanDto::toDto).toList();
    }


}
