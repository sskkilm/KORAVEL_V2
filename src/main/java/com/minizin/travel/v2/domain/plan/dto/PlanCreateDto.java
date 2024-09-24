package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import com.minizin.travel.v2.domain.plan.enums.Visibility;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record PlanCreateDto(String a, String b) {

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
            Integer numberOfMembers,
            @NotNull
            List<ScheduleCreateDto.Request> schedules
    ) {
        public static Plan toEntity(Request request, UserEntity user) {
            Plan plan = Plan.builder()
                    .user(user)
                    .title(request.title())
                    .thema(request.thema())
                    .startDate(request.startDate())
                    .endDate(request.endDate())
                    .visibility(request.visibility())
                    .numberOfMembers(request.numberOfMembers())
                    .build();

            for (ScheduleCreateDto.Request scheduleDtoRequest : request.schedules()) {
                Schedule schedule = ScheduleCreateDto.Request.toEntity(scheduleDtoRequest);
                schedule.addPlan(plan);
            }

            return plan;
        }
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
            Integer numberOfMembers,
            List<ScheduleCreateDto.Response> schedules
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
                    .schedules(
                            plan.getScheduleList().stream()
                                    .map(ScheduleCreateDto.Response::toDto).toList()
                    )
                    .build();
        }
    }
}
