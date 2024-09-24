package com.minizin.travel.v2.domain.plan.controller;

import com.minizin.travel.v2.domain.plan.dto.ScheduleUpdateDto;
import com.minizin.travel.v2.domain.plan.service.ScheduleService;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PutMapping("/{id}")
    public ScheduleUpdateDto.Response updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateDto.Request request
    ) {
        return scheduleService.updateSchedule(id, request, UserEntity.builder().id(1L).build());
    }
}
