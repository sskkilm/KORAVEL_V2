package com.minizin.travel.v1.plan.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.minizin.travel.v1.plan.entity.PlanBudget;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PlanBudgetDto {

    private String budgetCategory;

    private int cost;

    public static PlanBudgetDto toDto(PlanBudget budget) {
        return PlanBudgetDto.builder()
                .budgetCategory(budget.getBudgetCategory())
                .cost(budget.getCost())
                .build();
    }
}
