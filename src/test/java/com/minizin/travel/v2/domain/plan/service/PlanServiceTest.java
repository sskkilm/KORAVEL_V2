package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.*;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.enums.Visibility;
import com.minizin.travel.v2.domain.plan.repository.PlanRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    PlanRepository planRepository;

    @InjectMocks
    PlanService planService;

    @Test
    @DisplayName("여행 계획 생성 성공")
    void createPlan_success() {
        // given
        LocalTime time = LocalTime.of(11, 22);
        LocalDate date = LocalDate.of(2024, 9, 17);

        List<BudgetDto.Request> budgetDtoRequestList = List.of(
                BudgetDto.Request.builder()
                        .purpose("purpose")
                        .amount(10000)
                        .build()
        );

        List<PlaceDto.Request> placeDtoRequestList = List.of(
                PlaceDto.Request.builder()
                        .description("description")
                        .name("name")
                        .address("address")
                        .arrivalTime(time)
                        .memo("memo")
                        .x(1.0)
                        .y(1.0)
                        .budgetDtoRequestList(budgetDtoRequestList)
                        .build()
        );

        List<ScheduleDto.Request> scheduleDtoRequestList = List.of(
                ScheduleDto.Request.builder()
                        .date(date)
                        .placeDtoRequestList(placeDtoRequestList)
                        .build()
        );

        PlanCreateDto.Request request = PlanCreateDto.Request.builder()
                .title("title")
                .thema("thema")
                .startDate(date)
                .endDate(date)
                .visibility(Visibility.PUBLIC)
                .numberOfMembers(1)
                .scheduleDtoRequestList(scheduleDtoRequestList)
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        given(planRepository.save(any(Plan.class)))
                .willReturn(PlanCreateDto.Request.toEntity(request, user));

        // when
        PlanCreateDto.Response response = planService.createPlan(request);

        // then
        assertEquals(1L, response.userId());
        assertEquals("title", response.title());
        assertEquals("thema", response.thema());
        assertEquals(date, response.startDate());
        assertEquals(date, response.endDate());
        assertEquals(Visibility.PUBLIC, response.visibility());
        assertEquals(1, response.numberOfMembers());

        ScheduleDto.Response scheduleDtoResponse = response.scheduleDtoResponseList().get(0);
        assertEquals(date, scheduleDtoResponse.date());

        PlaceDto.Response placeDtoResponse = scheduleDtoResponse.placeDtoResponseList().get(0);
        assertEquals("description", placeDtoResponse.description());
        assertEquals("name", placeDtoResponse.name());
        assertEquals("address", placeDtoResponse.address());
        assertEquals("memo", placeDtoResponse.memo());
        assertEquals(time, placeDtoResponse.arrivalTime());
        assertEquals(1.0, placeDtoResponse.x());
        assertEquals(1.0, placeDtoResponse.y());

        BudgetDto.Response budgetDtoResponse = placeDtoResponse.budgetDtoResponseList().get(0);
        assertEquals("purpose", budgetDtoResponse.purpose());
        assertEquals(10000, budgetDtoResponse.amount());
    }

    @Test
    @DisplayName("여행 계획 생성 실패 - 유효하지 않은 날짜 정보")
    void createPlan_fail_InvalidDate() {
        // given
        LocalDate startDate = LocalDate.of(2024, 9, 17);
        LocalDate endDate = LocalDate.of(2024, 9, 16);

        PlanCreateDto.Request request = PlanCreateDto.Request.builder()
                .title("title")
                .thema("thema")
                .startDate(startDate)
                .endDate(endDate)
                .visibility(Visibility.PUBLIC)
                .numberOfMembers(1)
                .build();

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.createPlan(request)
        );

        // then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 수정 성공")
    void updatePlan_success() {
        //given
        LocalDate date = LocalDate.of(2024, 9, 17);
        LocalDate updatedDate = LocalDate.of(2024, 9, 18);

        PlanUpdateDto.Request request = PlanUpdateDto.Request.builder()
                .title("updatedTitle")
                .thema("updatedThema")
                .startDate(updatedDate)
                .endDate(updatedDate)
                .visibility(Visibility.PRIVATE)
                .numberOfMembers(2)
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        given(planRepository.findById(1L))
                .willReturn(Optional.of(
                        Plan.builder()
                                .user(user)
                                .title("title")
                                .thema("thema")
                                .startDate(date)
                                .endDate(date)
                                .visibility(Visibility.PUBLIC)
                                .numberOfMembers(1)
                                .build()
                ));

        //when
        PlanUpdateDto.Response response = planService.updatePlan(1L, request);

        //then
        assertEquals("updatedTitle", response.title());
        assertEquals("updatedThema", response.thema());
        assertEquals(updatedDate, response.startDate());
        assertEquals(updatedDate, response.endDate());
        assertEquals(Visibility.PRIVATE, response.visibility());
        assertEquals(2, response.numberOfMembers());
    }

    @Test
    @DisplayName("여행 계획 수정 실패 - 유효하지 않은 날짜 정보")
    void updatePlan_fail_InvalidDate() {
        // given
        LocalDate startDate = LocalDate.of(2024, 9, 17);
        LocalDate endDate = LocalDate.of(2024, 9, 16);

        PlanUpdateDto.Request request = PlanUpdateDto.Request.builder()
                .title("title")
                .thema("thema")
                .startDate(startDate)
                .endDate(endDate)
                .visibility(Visibility.PUBLIC)
                .numberOfMembers(1)
                .build();

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.updatePlan(1L, request)
        );

        // then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 수정 실패 - 존재하지 않는 계획")
    void updatePlan_fail_PlanNotFound() {
        // given
        LocalDate date = LocalDate.of(2024, 9, 17);

        PlanUpdateDto.Request request = PlanUpdateDto.Request.builder()
                .title("title")
                .thema("thema")
                .startDate(date)
                .endDate(date)
                .visibility(Visibility.PUBLIC)
                .numberOfMembers(1)
                .build();
        given(planRepository.findById(1L))
                .willReturn(Optional.empty());

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.updatePlan(1L, request)
        );

        // then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 삭제 성공")
    void deletePlan_success() {
        //given
        given(planRepository.findById(1L))
                .willReturn(Optional.of(
                        Plan.builder()
                                .title("title")
                                .thema("thema")
                                .build()
                ));

        //when
        PlanDeleteResponseDto planDeleteResponseDto = planService.deletePlan(1L);

        //then
        assertEquals("success", planDeleteResponseDto.message());
    }

    @Test
    @DisplayName("여행 계획 삭제 실패")
    void deletePlan_fail() {
        //given
        given(planRepository.findById(1L))
                .willReturn(Optional.empty());

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.deletePlan(1L)
        );

        //then
        assertNotNull(illegalArgumentException);
    }
}