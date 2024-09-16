package com.minizin.travel.v2.domain.plan.entity;

import com.minizin.travel.v2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

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
}
