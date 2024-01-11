package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.triprecord.entity.type.HashTagType;
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
public class TripRecordTag extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_tag_id")
    private Long id;

    private String hashTagType;

    @ManyToOne
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @Builder
    public TripRecordTag(Long id, String hashTagType, TripRecord tripRecord) {
        this.id = id;
        this.hashTagType = hashTagType;
        this.tripRecord = tripRecord;
    }
}
