package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Budget;
import com.minizin.travel.v2.domain.plan.entity.Place;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public record PlaceDto() {

    @Builder
    public record Request(
            String description,
            String name,
            String address,
            LocalTime arrivalTime,
            String memo,
            Double x,
            Double y,
            List<BudgetDto.Request> budgetDtoRequestList
    ) {
        public static Place toEntity(Request placeDtoRequest) {
            Place place = Place.builder()
                    .description(placeDtoRequest.description())
                    .name(placeDtoRequest.name())
                    .address(placeDtoRequest.address())
                    .arrivalTime(placeDtoRequest.arrivalTime())
                    .memo(placeDtoRequest.memo())
                    .x(placeDtoRequest.x())
                    .y(placeDtoRequest.y())
                    .build();

            for (BudgetDto.Request budgetDtoRequest : placeDtoRequest.budgetDtoRequestList()) {
                Budget budget = BudgetDto.Request.toEntity(budgetDtoRequest);
                budget.addPlace(place);
            }

            return place;
        }
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
            Double y,
            List<BudgetDto.Response> budgetDtoResponseList
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
                    .budgetDtoResponseList(
                            place.getBudgetList().stream()
                                    .map(BudgetDto.Response::toDto).toList()
                    )
                    .build();
        }
    }
}
