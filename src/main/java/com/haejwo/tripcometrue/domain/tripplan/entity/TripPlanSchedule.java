package com.haejwo.tripcometrue.domain.tripplan.entity;

import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "trip_plan_id")
    private TripPlan tripPlan;

    @Enumerated(EnumType.STRING)
    private ExternalLinkTagType tagType;
    private String tagUrl;

    @Builder
    public TripPlanSchedule(Integer dayNumber, Integer ordering, String content,
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
