package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import lombok.Builder;

public record TripRecordScheduleImageResponseDto(
    Long id,
    String imageUrl
) {

    @Builder
    public TripRecordScheduleImageResponseDto(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public static TripRecordScheduleImageResponseDto fromEntity(TripRecordScheduleImage entity) {
        return TripRecordScheduleImageResponseDto.builder()
            .id(entity.getId())
            .imageUrl(entity.getImageUrl())
            .build();
    }


}
