package com.minizin.travel.v2.domain.plan.entity;

import com.minizin.travel.v2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String memo;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @OneToMany(mappedBy = "place", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("id")
    private List<Budget> budgetList = new ArrayList<>();

    @Builder
    public Place(Schedule schedule, String description, String name, String address, String memo, LocalTime arrivalTime, Double x, Double y) {
        this.schedule = schedule;
        this.description = description;
        this.name = name;
        this.address = address;
        this.memo = memo;
        this.arrivalTime = arrivalTime;
        this.x = x;
        this.y = y;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getPlaceList().add(this);
    }

    public void update(String description, String name, String address, LocalTime arrivalTime,
                       String memo, Double x, Double y) {
        this.description = description;
        this.name = name;
        this.address = address;
        this.memo = memo;
        this.arrivalTime = arrivalTime;
        this.x = x;
        this.y = y;
    }
}
