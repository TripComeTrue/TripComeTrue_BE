package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;

public record TripRecordImageRequestDto(

    String imageUrl,
    ExternalLinkTagType tagType,
    String tagUrl
) {

    public TripRecordImage toEntity(TripRecord tripRecord) {
        return TripRecordImage.builder()
            .imageUrl(this.imageUrl)
            .tagType(this.tagType)
            .tagUrl(this.tagUrl)
            .tripRecord(tripRecord)
            .build();

    }
}
