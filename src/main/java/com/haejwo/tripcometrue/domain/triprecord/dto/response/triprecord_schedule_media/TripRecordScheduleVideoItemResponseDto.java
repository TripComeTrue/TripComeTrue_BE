package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

public record TripRecordScheduleVideoItemResponseDto(
    Long tripRecordId,
    String tripRecordTitle,
    Integer storedCount,
    String thumbnail
) {
}
