package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;

public record PlaceUpdateDto() {

    @Builder
    public record Request(
            @NotBlank
            String description,
            @NotBlank
            String name,
            @NotBlank
            String address,
            @NotNull
            LocalTime arrivalTime,
            @NotBlank
            String memo,
            @NotNull
            Double x,
            @NotNull
            Double y
    ) {

    }

    @Builder
    public record Response(
            Long placeId,
            String description,
            String name,
            String address,
            LocalTime arrivalTime,
            String memo,
            Double x,
            Double y
    ) {

        public static Response toDto(Place place) {
            return Response.builder()
                    .placeId(place.getId())
                    .description(place.getDescription())
                    .name(place.getName())
                    .address(place.getAddress())
                    .arrivalTime(place.getArrivalTime())
                    .memo(place.getMemo())
                    .x(place.getX())
                    .y(place.getY())
                    .build();
        }
    }
}
