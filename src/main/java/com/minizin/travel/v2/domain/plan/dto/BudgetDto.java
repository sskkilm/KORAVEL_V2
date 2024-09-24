package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Budget;
import lombok.Builder;

@Builder
public record BudgetDto(
        Long budgetId,
        String purpose,
        Integer amount
) {
    public static BudgetDto toDto(Budget budget) {

        return BudgetDto.builder()
                .budgetId(budget.getId())
                .purpose(budget.getPurpose())
                .amount(budget.getAmount())
                .build();
    }
}
