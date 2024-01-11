package com.haejwo.tripcometrue.domain.triprecord.entity;


import com.haejwo.tripcometrue.domain.place.entity.Place;
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
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordSchedule extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_schedule_id")
    private Long id;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private Integer ordering;

    private String content;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @OneToMany(mappedBy = "tripRecordSchedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordScheduleImage> tripRecordScheduleImages = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecordSchedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordScheduleVideo> tripRecordScheduleVideos = new ArrayList<>();

    @Builder
    public TripRecordSchedule(Long id, Integer dayNumber, Integer ordering, String content,
        Place place, TripRecord tripRecord, List<TripRecordScheduleImage> tripRecordScheduleImages,
        List<TripRecordScheduleVideo> tripRecordScheduleVideos) {

        this.id = id;
        this.dayNumber = dayNumber;
        this.ordering = ordering;
        this.content = content;
        this.place = place;
        this.tripRecord = tripRecord;
        this.tripRecordScheduleImages = tripRecordScheduleImages;
        this.tripRecordScheduleVideos = tripRecordScheduleVideos;
    }
}
