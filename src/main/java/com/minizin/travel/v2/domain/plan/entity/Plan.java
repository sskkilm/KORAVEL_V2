package com.minizin.travel.v2.domain.plan.entity;

import com.minizin.travel.v2.domain.plan.enums.Visibility;
import com.minizin.travel.v2.domain.user.entity.UserEntity;
import com.minizin.travel.v2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
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
public class Plan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String thema;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer numberOfMembers;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Schedule> scheduleList = new ArrayList<>();

    @Builder
    public Plan(UserEntity user, String thema, String title, LocalDate startDate, LocalDate endDate, Integer numberOfMembers, Visibility visibility) {
        this.user = user;
        this.thema = thema;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfMembers = numberOfMembers;
        this.visibility = visibility;
    }
}
