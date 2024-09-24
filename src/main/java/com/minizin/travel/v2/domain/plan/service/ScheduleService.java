package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.ScheduleUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import com.minizin.travel.v2.domain.plan.repository.ScheduleRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleUpdateDto.Response updateSchedule(Long scheduleId, ScheduleUpdateDto.Request request, UserEntity user) {
        Schedule schedule = scheduleRepository.findByIdWithPlan(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("schedule not found"));

        if (!Objects.equals(schedule.getPlan().getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("user schedule unmatched");
        }

        schedule.update(request.date());

        return ScheduleUpdateDto.Response.toDto(schedule);
    }
}
