package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.NewestTripRecordScheduleVideoQueryDto;
import lombok.Builder;

public record NewestTripRecordScheduleVideoResponseDto(
    Long id,
    Long tripRecordId,
    String tripRecordTitle,
    String videoUrl,
    String thumbnailUrl,
    Integer storeCount,
    Long memberId,
    String memberName
) {

    @Builder
    public NewestTripRecordScheduleVideoResponseDto {
    }

    public static NewestTripRecordScheduleVideoResponseDto fromQueryDto(NewestTripRecordScheduleVideoQueryDto queryDto) {
        return NewestTripRecordScheduleVideoResponseDto.builder()
            .id(queryDto.id())
            .tripRecordId(queryDto.tripRecordId())
            .tripRecordTitle(queryDto.tripRecordTitle())
            .videoUrl(queryDto.videoUrl())
            .thumbnailUrl(queryDto.thumbnailUrl())
            .storeCount(queryDto.storeCount())
            .memberId(queryDto.memberId())
            .memberName(queryDto.memberName())
            .build();
    }
}
