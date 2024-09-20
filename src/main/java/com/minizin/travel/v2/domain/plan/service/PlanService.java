package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.repository.PlanRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public PlanCreateDto.Response createPlan(PlanCreateDto.Request request) {

        if (request.startDate().isAfter(request.endDate())) {
            throw new IllegalArgumentException("날짜 정보가 유효하지 않습니다.");
        }

        return PlanCreateDto.Response.toDto(
                planRepository.save(
                        PlanCreateDto.Request.toEntity(request, UserEntity.builder().id(1L).build())
                )
        );
    }
}
