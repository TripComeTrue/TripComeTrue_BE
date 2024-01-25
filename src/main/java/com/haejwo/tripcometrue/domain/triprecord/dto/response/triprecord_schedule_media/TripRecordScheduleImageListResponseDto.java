package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import lombok.Builder;

public record TripRecordScheduleImageListResponseDto(
    Long tripRecordId,
    String imageUrl,
    Integer tripRecordStoreCount
) {

    @Builder
    public TripRecordScheduleImageListResponseDto(Long tripRecordId, String imageUrl,
        Integer tripRecordStoreCount) {
        this.tripRecordId = tripRecordId;
        this.imageUrl = imageUrl;
        this.tripRecordStoreCount = tripRecordStoreCount;
    }
}
