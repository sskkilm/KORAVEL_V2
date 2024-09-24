package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.PlaceUpdateDto;
import com.minizin.travel.v2.domain.plan.service.PlaceService;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/places")
public class PlaceController {

    private final PlaceService placeService;

    @PutMapping("/{id}")
    public PlaceUpdateDto.Response updatePlace(
            @PathVariable Long id,
            @Valid @RequestBody PlaceUpdateDto.Request request
    ) {
        return placeService.updatePlace(id, request, UserEntity.builder().id(1L).build());
    }
}
