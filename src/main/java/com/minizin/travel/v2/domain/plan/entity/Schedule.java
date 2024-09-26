package com.minizin.travel.v2.domain.plan.entity;

import com.minizin.travel.v2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("id")
    private List<Place> placeList = new ArrayList<>();

    @Builder
    public Schedule(Plan plan, LocalDate date) {
        this.plan = plan;
        this.date = date;
    }

    public void addPlan(Plan plan) {
        this.plan = plan;
        plan.getScheduleList().add(this);
    }

    public void update(LocalDate date) {
        this.date = date;
    }
}