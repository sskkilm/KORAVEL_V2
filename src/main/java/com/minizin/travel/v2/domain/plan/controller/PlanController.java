package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class PlanController {

    private final PlanService planService;

    @PostMapping("/plans")
    public PlanCreateDto.Response createPlan(
            @RequestBody PlanCreateDto.Request request
    ) {
        return planService.createPlan(request);
    }
}
