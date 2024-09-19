package com.minizin.travel.v2.domain.plan.entity;

import com.minizin.travel.v2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private Integer amount;

    @Builder
    public Budget(Place place, String purpose, Integer amount) {
        this.place = place;
        this.purpose = purpose;
        this.amount = amount;
    }

    public void addPlace(Place place) {
        this.place = place;
        place.getBudgetList().add(this);
    }
}
