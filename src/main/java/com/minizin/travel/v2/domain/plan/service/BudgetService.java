package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.BudgetUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Budget;
import com.minizin.travel.v2.domain.plan.repository.BudgetRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional
    public BudgetUpdateDto.Response updateBudget(Long budgetId, BudgetUpdateDto.Request request, UserEntity user) {
        Budget budget = budgetRepository.findByIdWithPlaceAndScheduleAndPlan(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("budget not found"));

        if (!Objects.equals(budget.getPlace().getSchedule().getPlan().getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("user budget unmatched");
        }

        budget.update(request.purpose(), request.amount());

        return BudgetUpdateDto.Response.toDto(budget);
    }
}
