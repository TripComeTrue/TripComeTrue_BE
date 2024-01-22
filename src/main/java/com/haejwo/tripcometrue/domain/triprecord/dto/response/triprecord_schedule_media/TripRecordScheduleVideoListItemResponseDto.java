package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordScheduleVideoQueryDto;
import lombok.Builder;

public record TripRecordScheduleVideoListItemResponseDto(
    Long videoId,
    Long tripRecordId,
    String tripRecordTitle,
    String thumbnailUrl,
    String videoUrl,
    Integer storedCount,
    Long memberId
) {

    @Builder
    public TripRecordScheduleVideoListItemResponseDto {
    }

    public static TripRecordScheduleVideoListItemResponseDto fromQueryDto(TripRecordScheduleVideoQueryDto dto) {
        return TripRecordScheduleVideoListItemResponseDto.builder()
            .videoId(dto.id())
            .tripRecordId(dto.tripRecordId())
            .tripRecordTitle(dto.tripRecordTitle())
            .thumbnailUrl(dto.thumbnailUrl())
            .videoUrl(dto.videoUrl())
            .storedCount(dto.storedCount())
            .memberId(dto.memberId())
            .build();
    }
}
