package com.haejwo.tripcometrue.domain.triprecord.dto.response.schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import lombok.Builder;

public record TripRecordScheduleVideoResponseDto(
    Long id,
    String videoUrl
) {

    @Builder
    public TripRecordScheduleVideoResponseDto(Long id, String videoUrl) {
        this.id = id;
        this.videoUrl = videoUrl;
    }

    public static TripRecordScheduleVideoResponseDto fromEntity(TripRecordScheduleVideo entity) {
        return TripRecordScheduleVideoResponseDto.builder()
            .id(entity.getId())
            .videoUrl(entity.getVideoUrl())
            .build();
    }

}
