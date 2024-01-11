package com.haejwo.tripcometrue.domain.triprecord.dto.response.record_media_tag;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordTag;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.HashTagType;
import lombok.Builder;

public record TripRecordTagResponseDto(
    Long id,
    String hashTagType,
    Long tripRecordId
) {

    @Builder
    public TripRecordTagResponseDto(Long id, String hashTagType, Long tripRecordId) {
        this.id = id;
        this.hashTagType = hashTagType;
        this.tripRecordId = tripRecordId;
    }

    public static TripRecordTagResponseDto fromEntity(TripRecordTag entity) {
        return TripRecordTagResponseDto.builder()
            .id(entity.getId())
            .hashTagType(entity.getHashTagType())
            .tripRecordId(entity.getTripRecord().getId())
            .build();
    }

}
