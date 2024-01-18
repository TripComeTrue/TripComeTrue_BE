package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import lombok.Builder;

public record TripRecordScheduleImageListResponseDto(
    Long tripRecordId,
    String imageUrl,
    Integer TripRecordStoreNum
) {

    @Builder
    public TripRecordScheduleImageListResponseDto(Long tripRecordId, String imageUrl,
        Integer TripRecordStoreNum) {
        this.tripRecordId = tripRecordId;
        this.imageUrl = imageUrl;
        this.TripRecordStoreNum = TripRecordStoreNum;
    }
}
