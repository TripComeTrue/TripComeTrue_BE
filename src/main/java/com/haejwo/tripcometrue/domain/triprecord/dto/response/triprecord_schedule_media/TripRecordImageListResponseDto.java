package com.haejwo.tripcometrue.domain.triprecord.dto.response.schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import lombok.Builder;

public record TripRecordImageListResponseDto(
    Long tripRecordId,
    String imageUrl
) {

    @Builder
    public TripRecordImageListResponseDto(Long tripRecordId, String imageUrl) {
        this.tripRecordId = tripRecordId;
        this.imageUrl = imageUrl;
    }
}
