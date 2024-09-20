package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
