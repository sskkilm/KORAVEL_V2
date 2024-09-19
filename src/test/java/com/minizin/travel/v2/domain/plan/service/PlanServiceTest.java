package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.BudgetDto;
import com.minizin.travel.v2.domain.plan.dto.PlaceDto;
import com.minizin.travel.v2.domain.plan.dto.PlanCreateDto;
import com.minizin.travel.v2.domain.plan.dto.ScheduleDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}