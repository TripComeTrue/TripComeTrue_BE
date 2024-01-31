package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordViewCount extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_view_count_id")
    private Long id;

    private Integer viewCount;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @Builder
    private TripRecordViewCount(Long id, Integer viewCount, LocalDate date, TripRecord tripRecord) {
        this.id = id;
        this.viewCount = viewCount;
        this.date = date;
        this.tripRecord = tripRecord;
    }

    public void incrementViewCount() {
        if(this.viewCount == null) {
            this.viewCount = 1;
        } else {
            this.viewCount++;
        }
    }
}
