package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.enums.Visibility;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PlanDto(
        String title,
        String thema,
        LocalDate startDate,
        LocalDate endDate,
        Visibility visibility,
        Integer numberOfMembers,
        List<ScheduleDto.Response> scheduleDtoResponseList
) {
    public static PlanDto toDto(Plan plan) {
        return PlanDto.builder()
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
