package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_schedule_id")
    private Long id;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private Integer ordering;

    private String content;

    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "tripRecord_id")
    private TripRecord tripRecord;

    @OneToMany(mappedBy = "tripRecordSchedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordScheduleImage> tripRecordScheduleImages = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecordSchedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordScheduleVideo> tripRecordScheduleVideos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ExternalLinkTagType tagType;
    private String tagUrl;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public TripRecordSchedule(Integer dayNumber, Integer ordering, String content,
        Long placeId, TripRecord tripRecord, ExternalLinkTagType tagType, String tagUrl,
        Member member) {
        this.dayNumber = dayNumber;
        this.ordering = ordering;
        this.content = content;
        this.placeId = placeId;
        this.tripRecord = tripRecord;
        this.tagType = tagType;
        this.tagUrl = tagUrl;
        this.member = member;

    }
}
