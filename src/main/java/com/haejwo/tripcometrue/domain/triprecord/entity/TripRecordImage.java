package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.TripRecordImageType;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_image_id")
    private Long id;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ExternalLinkTagType tagType;
    private String tagUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @Builder
    private TripRecordImage(TripRecordImageType imageType, String imageUrl,
        ExternalLinkTagType tagType, String tagUrl, TripRecord tripRecord) {
        this.imageUrl = imageUrl;
        this.tagType = tagType;
        this.tagUrl = tagUrl;
        this.tripRecord = tripRecord;
    }
}
