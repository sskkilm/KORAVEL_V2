package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.enums.Visibility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

public record PlanUpdateDto(String a, String b) {

    @Builder
    public record Request(
            @NotBlank
            String title,
            @NotBlank
            String thema,
            @NotNull
            LocalDate startDate,
            @NotNull
            LocalDate endDate,
            @NotNull
            Visibility visibility,
            @Min(1)
            Integer numberOfMembers
    ) {
    }

    @Builder
    public record Response(
            String message,
            Long planId,
            Long userId,
            String title,
            String thema,
            LocalDate startDate,
            LocalDate endDate,
            Visibility visibility,
            Integer numberOfMembers
    ) {
        public static Response toDto(Plan plan) {
            return Response.builder()
                    .message("success")
                    .planId(plan.getId())
                    .userId(plan.getUser().getId())
                    .title(plan.getTitle())
                    .thema(plan.getThema())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .visibility(plan.getVisibility())
                    .numberOfMembers(plan.getNumberOfMembers())
                    .build();
        }
    }
}
