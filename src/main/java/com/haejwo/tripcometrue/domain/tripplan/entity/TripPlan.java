package com.haejwo.tripcometrue.domain.tripplan.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.tripplan.dto.request.TripPlanRequestDto;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripPlan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_plan_id")
    private Long id;

    private String countries;
    private LocalDate tripStartDay;
    private LocalDate tripEndDay;
    private Integer totalDays;
    private Integer averageRating;
    private Integer viewCount;
    private Long referencedBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "tripPlan", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripPlanSchedule> tripPlanSchedules = new ArrayList<>();

    @Builder
    private TripPlan(String countries, LocalDate tripStartDay, LocalDate tripEndDay,
        Integer totalDays, Integer averageRating, Integer viewCount,
        List<TripPlanSchedule> tripRecordSchedules, Member member, Long referencedBy) {
        this.countries = countries;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.totalDays = (int)ChronoUnit.DAYS.between(tripStartDay, tripEndDay)+1;
        this.averageRating = averageRating;
        this.viewCount = 0;
        this.tripPlanSchedules = tripRecordSchedules;
        this.member = member;
        this.referencedBy = referencedBy;
    }

    public void update(TripPlanRequestDto requestDto) {
        this.countries = requestDto.countries();
        this.tripStartDay = requestDto.tripStartDay();
        this.tripEndDay = requestDto.tripEndDay();
        this.totalDays = (int)ChronoUnit.DAYS.between(tripStartDay, tripEndDay)+1;
    }
}
