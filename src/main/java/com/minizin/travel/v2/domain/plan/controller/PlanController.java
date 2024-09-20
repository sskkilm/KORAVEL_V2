package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.dto.PlanUpdateDto;
import com.minizin.travel.v2.domain.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/plans")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public PlanCreateDto.Response createPlan(
            @Valid @RequestBody PlanCreateDto.Request request
    ) {
        return planService.createPlan(request);
    }

    @PutMapping("/{id}")
    public PlanUpdateDto.Response updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody PlanUpdateDto.Request request
    ) {
        return planService.updatePlan(id, request);
    }
}
