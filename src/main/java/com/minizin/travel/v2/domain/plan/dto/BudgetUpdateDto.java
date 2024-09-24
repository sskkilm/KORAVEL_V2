package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Budget;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record BudgetUpdateDto() {

    @Builder
    public record Request(
            @NotBlank
            String purpose,
            @Min(0)
            Integer amount
    ) {
    }

    @Builder
    public record Response(
            Long budgetId,
            String purpose,
            Integer amount
    ) {

        public static Response toDto(Budget budget) {

            return Response.builder()
                    .budgetId(budget.getId())
                    .purpose(budget.getPurpose())
                    .amount(budget.getAmount())
                    .build();
        }
    }
}
