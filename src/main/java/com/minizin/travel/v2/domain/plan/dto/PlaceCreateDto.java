package com.minizin.travel.v2.domain.plan.dto;

import com.minizin.travel.v2.domain.plan.entity.Budget;
import com.minizin.travel.v2.domain.plan.entity.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

public record PlaceCreateDto() {

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
            Double y,
            @NotNull
            List<BudgetCreateDto.Request> budgets
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

            for (BudgetCreateDto.Request budgetDtoRequest : placeDtoRequest.budgets()) {
                Budget budget = BudgetCreateDto.Request.toEntity(budgetDtoRequest);
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
            List<BudgetCreateDto.Response> budgets
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
                    .budgets(
                            place.getBudgetList().stream()
                                    .map(BudgetCreateDto.Response::toDto).toList()
                    )
                    .build();
        }
    }
}
