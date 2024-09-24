package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

public record ScheduleUpdateDto() {

    @Builder
    public record Request(
            @NotNull
            LocalDate date
    ) {
    }


    @Builder
    public record Response(
            Long scheduleId,
            LocalDate date
    ) {
        public static Response toDto(Schedule schedule) {
            return Response.builder()
                    .scheduleId(schedule.getId())
                    .date(schedule.getDate())
                    .build();
        }
    }
}
