package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.dto.PlanDeleteDto;
import com.minizin.travel.v2.domain.plan.dto.PlanDto;
import com.minizin.travel.v2.domain.plan.dto.PlanUpdateDto;
import com.minizin.travel.v2.domain.plan.service.PlanService;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/plans")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public PlanCreateDto.Response createPlan(
            @Valid @RequestBody PlanCreateDto.Request request
    ) {
        return planService.createPlan(request, UserEntity.builder().id(1L).build());
    }

    @PutMapping("/{id}")
    public PlanUpdateDto.Response updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody PlanUpdateDto.Request request
    ) {
        return planService.updatePlan(id, request, UserEntity.builder().id(1L).build());
    }

    @DeleteMapping("/{id}")
    public PlanDeleteDto deletePlan(@PathVariable Long id) {
        return planService.deletePlan(id, UserEntity.builder().id(1L).build());
    }

    @GetMapping("/me")
    public List<PlanDto> getMyPlanList() {
        return planService.getMyPlanList(UserEntity.builder().id(1L).build());
    }

    @GetMapping("/users")
    public Page<PlanDto> getAllPlanList(Pageable pageable) {
        return planService.getAllPlanList(pageable);
    }
}
