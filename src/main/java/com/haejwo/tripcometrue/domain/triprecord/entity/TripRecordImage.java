package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.TripRecordImageType;
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
import jakarta.persistence.OneToMany;
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

    private TripRecordImageType imageType;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ExternalLinkTagType tagType;
    private String tagUrl;

    @ManyToOne
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @Builder
    public TripRecordImage(Long id, TripRecordImageType imageType, String imageUrl,
        ExternalLinkTagType tagType, String tagUrl, TripRecord tripRecord) {
        this.id = id;
        this.imageType = imageType;
        this.imageUrl = imageUrl;
        this.tagType = tagType;
        this.tagUrl = tagUrl;
        this.tripRecord = tripRecord;
    }
}
