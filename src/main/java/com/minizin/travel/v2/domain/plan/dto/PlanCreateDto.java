package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import com.minizin.travel.v2.domain.plan.enums.Visibility;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record PlanCreateDto(String a, String b) {

    @Builder
    public record Request(
            String title,
            String thema,
            LocalDate startDate,
            LocalDate endDate,
            Visibility visibility,
            Integer numberOfMembers,
            List<ScheduleDto.Request> scheduleDtoRequestList
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

            for (ScheduleDto.Request scheduleDtoRequest : request.scheduleDtoRequestList()) {
                Schedule schedule = ScheduleDto.Request.toEntity(scheduleDtoRequest);
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
            List<ScheduleDto.Response> scheduleDtoResponseList
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
                    .scheduleDtoResponseList(
                            plan.getScheduleList().stream()
                                    .map(ScheduleDto.Response::toDto).toList()
                    )
                    .build();
        }
    }
}
