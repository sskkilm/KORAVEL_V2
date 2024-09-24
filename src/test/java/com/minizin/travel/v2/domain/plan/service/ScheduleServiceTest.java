package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.ScheduleUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import com.minizin.travel.v2.domain.plan.repository.ScheduleRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @InjectMocks
    ScheduleService scheduleService;

    @Test
    @DisplayName("일정 수정 성공")
    void updateSchedule_success() {
        //given
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalDate updateDate = LocalDate.of(2024, 1, 1);

        ScheduleUpdateDto.Request request = ScheduleUpdateDto.Request.builder()
                .date(updateDate)
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        Plan plan = Plan.builder()
                .user(user)
                .build();

        given(scheduleRepository.findByIdWithPlan(1L))
                .willReturn(Optional.of(
                        Schedule.builder()
                                .plan(plan)
                                .date(date)
                                .build()
                ));

        //when
        ScheduleUpdateDto.Response response = scheduleService.updateSchedule(1L, request, user);

        //then
        assertEquals(updateDate, response.date());
    }

    @Test
    @DisplayName("일정 수정 실패 - 존재하지 않는 일정")
    void updateSchedule_fail_ScheduleNotFound() {
        //given
        given(scheduleRepository.findByIdWithPlan(1L))
                .willReturn(Optional.empty());

        ScheduleUpdateDto.Request request = ScheduleUpdateDto.Request.builder().build();
        UserEntity user = UserEntity.builder().build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> scheduleService.updateSchedule(1L, request, user));

        //then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("일정 수정 실패 - 사용자의 일정이 아님")
    void updateSchedule_fail_UserScheduleUnmatched() {
        //given
        LocalDate date = LocalDate.of(2023, 1, 1);

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        Plan plan = Plan.builder()
                .user(user)
                .build();

        given(scheduleRepository.findByIdWithPlan(1L))
                .willReturn(Optional.of(
                        Schedule.builder()
                                .plan(plan)
                                .date(date)
                                .build()
                ));

        ScheduleUpdateDto.Request request = ScheduleUpdateDto.Request.builder().build();
        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> scheduleService.updateSchedule(1L, request, user2));

        //then
        assertNotNull(illegalArgumentException);
    }
}