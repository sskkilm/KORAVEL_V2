package com.minizin.travel.v2.domain.plan.service;

import com.minizin.travel.v2.domain.plan.dto.BudgetUpdateDto;
import com.minizin.travel.v2.domain.plan.entity.Budget;
import com.minizin.travel.v2.domain.plan.entity.Place;
import com.minizin.travel.v2.domain.plan.entity.Plan;
import com.minizin.travel.v2.domain.plan.entity.Schedule;
import com.minizin.travel.v2.domain.plan.repository.BudgetRepository;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    BudgetRepository budgetRepository;

    @InjectMocks
    BudgetService budgetService;

    @Test
    @DisplayName("예산 수정 성공")
    void updateBudget_success() {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();
        Plan plan = Plan.builder()
                .user(user)
                .build();
        Schedule schedule = Schedule.builder()
                .plan(plan)
                .build();
        Place place = Place.builder()
                .schedule(schedule)
                .build();

        given(budgetRepository.findById(1L))
                .willReturn(Optional.of(
                        Budget.builder()
                                .place(place)
                                .purpose("purpose")
                                .amount(10000)
                                .build()
                ));

        BudgetUpdateDto.Request request = BudgetUpdateDto.Request.builder()
                .purpose("purpose2")
                .amount(20000)
                .build();

        //when
        BudgetUpdateDto.Response response = budgetService.updateBudget(1L, request, user);

        //then
        assertEquals("purpose2", request.purpose());
        assertEquals(20000, request.amount());
    }

    @Test
    @DisplayName("예산 수정 실패 - 존재하지 않는 예산")
    void updateBudget_fail_BudgetNotFound() {
        //given
        given(budgetRepository.findById(1L))
                .willReturn(Optional.empty());

        BudgetUpdateDto.Request request = BudgetUpdateDto.Request.builder().build();
        UserEntity user = UserEntity.builder().build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> budgetService.updateBudget(1L, request, user));

        //then
        assertNotNull(illegalArgumentException);
    }

    @Test
    @DisplayName("예산 수정 실패 - 사용자의 예산이 아님")
    void updateBudget_fail_UserBudgetUnmatched() {
        //given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .build();
        Plan plan = Plan.builder()
                .user(user)
                .build();
        Schedule schedule = Schedule.builder()
                .plan(plan)
                .build();
        Place place = Place.builder()
                .schedule(schedule)
                .build();
        given(budgetRepository.findById(1L))
                .willReturn(Optional.of(
                        Budget.builder()
                                .place(place)
                                .purpose("purpose")
                                .amount(10000)
                                .build()
                ));

        BudgetUpdateDto.Request request = BudgetUpdateDto.Request.builder().build();
        UserEntity user2 = UserEntity.builder().id(2L).build();

        //when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> budgetService.updateBudget(1L, request, user2));

        //then
        assertNotNull(illegalArgumentException);
    }

}