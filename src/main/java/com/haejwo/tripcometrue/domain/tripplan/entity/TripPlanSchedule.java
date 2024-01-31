package com.haejwo.tripcometrue.domain.tripplan.entity;

import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripPlanSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_plan_schedule_id")
    private Long id;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private Integer ordering;

    private String content;
    private Long placeId;

    @Enumerated(EnumType.STRING)
    private ExternalLinkTagType tagType;
    private String tagUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_plan_id")
    private TripPlan tripPlan;

    @Builder
    private TripPlanSchedule(Integer dayNumber, Integer ordering, String content,
        Long placeId, TripPlan tripPlan, ExternalLinkTagType tagType, String tagUrl
    ) {
        this.dayNumber = dayNumber;
        this.ordering = ordering;
        this.content = content;
        this.placeId = placeId;
        this.tripPlan = tripPlan;
        this.tagType = tagType;
        this.tagUrl = tagUrl;
    }
}
