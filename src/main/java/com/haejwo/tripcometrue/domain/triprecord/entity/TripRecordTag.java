package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_tag_id")
    private Long id;

    private String hashTagType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @Builder
    private TripRecordTag(String hashTagType, TripRecord tripRecord) {
        this.hashTagType = hashTagType;
        this.tripRecord = tripRecord;
    }
}
