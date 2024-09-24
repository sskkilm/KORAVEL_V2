package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Place;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

@Builder
public record PlaceDto(
        Long placeId,
        String description,
        String name,
        String address,
        LocalTime arrivalTime,
        String memo,
        Double x,
        Double y,
        List<BudgetDto> budgets
) {

    public static PlaceDto toDto(Place place) {
        return PlaceDto.builder()
                .placeId(place.getId())
                .description(place.getDescription())
                .name(place.getName())
                .address(place.getAddress())
                .arrivalTime(place.getArrivalTime())
                .memo(place.getMemo())
                .x(place.getX())
                .y(place.getY())
                .budgets(
                        place.getBudgetList().stream()
                                .map(BudgetDto::toDto).toList()
                )
                .build();
    }
}
