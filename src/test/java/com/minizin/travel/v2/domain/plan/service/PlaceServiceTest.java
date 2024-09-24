package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.PlaceUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Place;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import com.minizin.travel.v2.domain.plan.repository.PlaceRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    PlaceService placeService;

    @Test
    @DisplayName("장소 수정 성공")
    void updatePlace_success() {
        //given
        LocalTime time = LocalTime.of(11, 22);

        UserEntity user = UserEntity.builder().id(1L).build();

        Plan plan = Plan.builder()
                .user(user)
                .build();

        Schedule schedule = Schedule.builder()
                .plan(plan)
                .build();

        given(placeRepository.findByIdWithScheduleAndPlan(1L))
                .willReturn(Optional.of(
                        Place.builder()
                                .schedule(schedule)
                                .description("description")
                                .name("name")
                                .address("address")
                                .arrivalTime(time)
                                .memo("memo")
                                .x(1.0)
                                .y(2.0)
                                .build()
                ));

        LocalTime updateTime = LocalTime.of(22, 33);
        PlaceUpdateDto.Request request = PlaceUpdateDto.Request.builder()
                .description("description2")
                .name("name2")
                .address("address2")
                .arrivalTime(updateTime)
                .memo("memo2")
                .x(2.0)
                .y(3.0)
                .build();

        //when
        PlaceUpdateDto.Response response = placeService.updatePlace(1L, request, user);

        //then
        assertEquals("description2", response.description());
        assertEquals("name2", response.name());
        assertEquals("address2", response.address());
        assertEquals(updateTime, response.arrivalTime());
        assertEquals("memo2", response.memo());
        assertEquals(2.0, response.x());
        assertEquals(3.0, response.y());
    }

    @Test
    @DisplayName("장소 수정 실패 - 존재하지 않는 장소")
    void updatePlace_fail_PlaceNotFound() {
        //given
        given(placeRepository.findByIdWithScheduleAndPlan(1L))
                .willReturn(Optional.empty());

        PlaceUpdateDto.Request request = PlaceUpdateDto.Request.builder().build();
        UserEntity user = UserEntity.builder().build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> placeService.updatePlace(1L, request, user));

        //then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("장소 수정 실패 - 사용자의 장소가 아님")
    void updatePlace_fail_UserPlaceUnmatched() {
        //given
        LocalTime time = LocalTime.of(11, 22);

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();
        Plan plan = Plan.builder()
                .user(user)
                .build();
        Schedule schedule = Schedule.builder()
                .plan(plan)
                .build();

        given(placeRepository.findByIdWithScheduleAndPlan(1L))
                .willReturn(Optional.of(
                        Place.builder()
                                .schedule(schedule)
                                .description("description")
                                .name("name")
                                .address("address")
                                .arrivalTime(time)
                                .memo("memo")
                                .x(1.0)
                                .y(2.0)
                                .build()
                ));

        PlaceUpdateDto.Request request = PlaceUpdateDto.Request.builder().build();
        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> placeService.updatePlace(1L, request, user2));

        //then
        assertNotNull(illegalArgumentException);
    }

}