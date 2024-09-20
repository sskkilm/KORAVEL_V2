package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Place;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record ScheduleDto() {

    @Builder
    public record Request(
            @NotNull
            LocalDate date,
            @NotNull
            List<PlaceDto.Request> placeDtoRequestList
    ) {
        public static Schedule toEntity(Request request) {
            Schedule schedule = Schedule.builder()
                    .date(request.date())
                    .build();

            for (PlaceDto.Request placeDtoRequest : request.placeDtoRequestList()) {
                Place place = PlaceDto.Request.toEntity(placeDtoRequest);
                place.addSchedule(schedule);
            }

            return schedule;
        }
    }


    @Builder
    public record Response(
            Long scheduleId,
            LocalDate date,
            List<PlaceDto.Response> placeDtoResponseList
    ) {
        public static Response toDto(Schedule schedule) {
            return Response.builder()
                    .scheduleId(schedule.getId())
                    .date(schedule.getDate())
                    .placeDtoResponseList(
                            schedule.getPlaceList().stream()
                                    .map(PlaceDto.Response::toDto).toList()
                    )
                    .build();
        }
    }
}
