package com.haejwo.tripcometrue.domain.triprecord.dto.query;

public record TripRecordScheduleVideoQueryDto(
    Long id,
    Long tripRecordId,
    String tripRecordTitle,
    String thumbnailUrl,
    String videoUrl,
    Integer storedCount,
    Long memberId,
    String memberName,
    String profileImageUrl
) {
}
