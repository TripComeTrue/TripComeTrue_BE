package com.haejwo.tripcometrue.domain.triprecord.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class TripRecordSchedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_schedule_id")
    private Long id;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private Integer ordering;

    private String content;

    @ManyToOne
    @JoinColumn(name = "tripRecord_id")
    private TripRecord tripRecord;

    @Builder
    public TripRecordSchedule(Long id, Integer dayNumber, Integer ordering, String content) {
        this.id = id;
        this.dayNumber = dayNumber;
        this.ordering = ordering;
        this.content = content;
    }


}
