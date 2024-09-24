package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Place;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public record ScheduleCreateDto() {

    @Builder
    public record Request(
            @NotNull
            LocalDate date,
            @NotNull
            List<PlaceCreateDto.Request> places
    ) {
        public static Schedule toEntity(Request request) {
            Schedule schedule = Schedule.builder()
                    .date(request.date())
                    .build();

            for (PlaceCreateDto.Request placeDtoRequest : request.places()) {
                Place place = PlaceCreateDto.Request.toEntity(placeDtoRequest);
                place.addSchedule(schedule);
            }

            return schedule;
        }
    }


    @Builder
    public record Response(
            Long scheduleId,
            LocalDate date,
            List<PlaceCreateDto.Response> places
    ) {
        public static Response toDto(Schedule schedule) {
            return Response.builder()
                    .scheduleId(schedule.getId())
                    .date(schedule.getDate())
                    .places(
                            schedule.getPlaceList().stream()
                                    .map(PlaceCreateDto.Response::toDto).toList()
                    )
                    .build();
        }
    }
}
