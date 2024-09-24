package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.BudgetUpdateDto;
import com.minizin.travel.v2.domain.plan.service.BudgetService;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PutMapping("/{id}")
    public BudgetUpdateDto.Response updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetUpdateDto.Request request
    ) {
        return budgetService.updateBudget(id, request, UserEntity.builder().id(1L).build());
    }
}
