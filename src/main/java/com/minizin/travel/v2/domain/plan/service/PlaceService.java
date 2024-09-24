package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.PlaceUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Place;
import com.minizin.travel.v2.domain.plan.repository.PlaceRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceUpdateDto.Response updatePlace(Long placeId, PlaceUpdateDto.Request request, UserEntity user) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("place not found"));

        if (!Objects.equals(place.getSchedule().getPlan().getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("user place unmatched");
        }

        place.update(request.description(), request.name(), request.address(), request.arrivalTime(),
                request.memo(), request.x(), request.y());

        return PlaceUpdateDto.Response.toDto(place);
    }
}
