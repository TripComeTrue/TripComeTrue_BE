package com.haejwo.tripcometrue.domain.triprecord.dto.response.trip_record_schedule_video;

public record TripRecordScheduleVideoResponseDto(
    Long tripRecordId,
    String tripRecordTitle,
    Integer storedCount,
    String thumbnail
) {
}
