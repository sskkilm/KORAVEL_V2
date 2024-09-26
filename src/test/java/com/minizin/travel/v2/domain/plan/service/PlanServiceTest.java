package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.*;
import com.minizin.travel.v2.domain.plan.entity.Budget;
import com.minizin.travel.v2.domain.plan.entity.Place;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
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

        List<BudgetCreateDto.Request> budgets = List.of(
                BudgetCreateDto.Request.builder()
                        .purpose("purpose")
                        .amount(10000)
                        .build()
        );

        List<PlaceCreateDto.Request> places = List.of(
                PlaceCreateDto.Request.builder()
                        .description("description")
                        .name("name")
                        .address("address")
                        .arrivalTime(time)
                        .memo("memo")
                        .x(1.0)
                        .y(1.0)
                        .budgets(budgets)
                        .build()
        );

        List<ScheduleCreateDto.Request> schedules = List.of(
                ScheduleCreateDto.Request.builder()
                        .date(date)
                        .places(places)
                        .build()
        );

        PlanCreateDto.Request request = PlanCreateDto.Request.builder()
                .title("title")
                .thema("thema")
                .startDate(date)
                .endDate(date)
                .visibility(Visibility.PUBLIC)
                .numberOfMembers(1)
                .schedules(schedules)
                .build();

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        given(planRepository.save(any(Plan.class)))
                .willReturn(PlanCreateDto.Request.toEntity(request, user));

        // when
        PlanCreateDto.Response response = planService.createPlan(request, user);

        // then
        assertEquals(1L, response.userId());
        assertEquals("title", response.title());
        assertEquals("thema", response.thema());
        assertEquals(date, response.startDate());
        assertEquals(date, response.endDate());
        assertEquals(Visibility.PUBLIC, response.visibility());
        assertEquals(1, response.numberOfMembers());

        ScheduleCreateDto.Response scheduleDtoResponse = response.schedules().get(0);
        assertEquals(date, scheduleDtoResponse.date());

        PlaceCreateDto.Response placeDtoResponse = scheduleDtoResponse.places().get(0);
        assertEquals("description", placeDtoResponse.description());
        assertEquals("name", placeDtoResponse.name());
        assertEquals("address", placeDtoResponse.address());
        assertEquals("memo", placeDtoResponse.memo());
        assertEquals(time, placeDtoResponse.arrivalTime());
        assertEquals(1.0, placeDtoResponse.x());
        assertEquals(1.0, placeDtoResponse.y());

        BudgetCreateDto.Response budgetDtoResponse = placeDtoResponse.budgets().get(0);
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

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.createPlan(request, user)
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
        PlanUpdateDto.Response response = planService.updatePlan(1L, request, user);

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

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.updatePlan(1L, request, user)
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

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.updatePlan(1L, request, user)
        );

        // then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 수정 실패 - 사용자 계획 불일치")
    void updatePlan_fail_UserPlanUnmatched() {
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

        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .build();

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.updatePlan(1L, request, user2)
        );

        // then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 삭제 성공")
    void deletePlan_success() {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        given(planRepository.findById(1L))
                .willReturn(Optional.of(
                        Plan.builder()
                                .user(user)
                                .title("title")
                                .thema("thema")
                                .build()
                ));

        //when
        PlanDeleteDto planDeleteDto = planService.deletePlan(1L, user);

        //then
        assertEquals("success", planDeleteDto.message());
    }

    @Test
    @DisplayName("여행 계획 삭제 실패 - 존재하지 않는 계획")
    void deletePlan_fail_PlanNotFound() {
        //given
        given(planRepository.findById(1L))
                .willReturn(Optional.empty());

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.deletePlan(1L, user)
        );

        //then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 삭제 실패 - 사용자 계획 불일치")
    void deletePlan_fail_UserPlanUnmatched() {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        given(planRepository.findById(1L))
                .willReturn(Optional.of(
                        Plan.builder()
                                .user(user)
                                .title("title")
                                .thema("thema")
                                .build()
                ));

        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> planService.deletePlan(1L, user2)
        );

        //then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("여행 계획 목록 조회")
    void getPlanList() {
        //given
        LocalTime time = LocalTime.of(11, 22);
        LocalDate date = LocalDate.of(2024, 9, 17);
        Plan plan = Plan.builder()
                .title("title")
                .thema("thema")
                .startDate(date)
                .endDate(date)
                .visibility(Visibility.PUBLIC)
                .numberOfMembers(1)
                .build();
        Schedule schedule = Schedule.builder()
                .date(date)
                .build();
        schedule.addPlan(plan);
        Place place = Place.builder()
                .description("description")
                .name("name")
                .address("address")
                .arrivalTime(time)
                .memo("memo")
                .x(1.0)
                .y(1.0)
                .build();
        place.addSchedule(schedule);
        Budget budget = Budget.builder()
                .purpose("purpose")
                .amount(10000)
                .build();
        budget.addPlace(place);

        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();

        given(planRepository.findAllByUserOrderById(user))
                .willReturn(List.of(plan));

        //when
        List<PlanDto> planDtoList = planService.getMyPlanList(user);

        //then
        assertEquals(1, planDtoList.size());
        PlanDto planDto = planDtoList.get(0);
        assertEquals("title", planDto.title());
        assertEquals("thema", planDto.thema());
        assertEquals(date, planDto.startDate());
        assertEquals(date, planDto.endDate());
        assertEquals(Visibility.PUBLIC, planDto.visibility());
        assertEquals(1, planDto.numberOfMembers());

        ScheduleDto scheduleDto = planDto.schedules().get(0);
        assertEquals(date, scheduleDto.date());

        PlaceDto placeDto = scheduleDto.places().get(0);
        assertEquals("description", placeDto.description());
        assertEquals("name", placeDto.name());
        assertEquals("address", placeDto.address());
        assertEquals("memo", placeDto.memo());
        assertEquals(time, placeDto.arrivalTime());
        assertEquals(1.0, placeDto.x());
        assertEquals(1.0, placeDto.y());

        BudgetDto budgetDto = placeDto.budgets().get(0);
        assertEquals("purpose", budgetDto.purpose());
        assertEquals(10000, budgetDto.amount());
    }
}