package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Schedule;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ScheduleDto(
        Long scheduleId,
        LocalDate date,
        List<PlaceDto> places
) {

    public static ScheduleDto toDto(Schedule schedule) {
        return ScheduleDto.builder()
                .scheduleId(schedule.getId())
                .date(schedule.getDate())
                .places(
                        schedule.getPlaceList().stream()
                                .map(PlaceDto::toDto).toList()
                )
                .build();
    }
}
