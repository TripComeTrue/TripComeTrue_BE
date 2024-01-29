package com.haejwo.tripcometrue.domain.triprecord.entity;


import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
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
public class TripRecordScheduleVideo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_schedule_video_id")
    private Long id;

    private String videoUrl;
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "trip_schedule_id")
    private TripRecordSchedule tripRecordSchedule;

    @Builder
    public TripRecordScheduleVideo(String videoUrl, String thumbnailUrl,
        TripRecordSchedule tripRecordSchedule) {
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.tripRecordSchedule = tripRecordSchedule;
    }
}
