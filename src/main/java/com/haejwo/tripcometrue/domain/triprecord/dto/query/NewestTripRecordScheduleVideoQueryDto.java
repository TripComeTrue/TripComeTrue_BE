package com.haejwo.tripcometrue.domain.triprecord.dto.query;


public record NewestTripRecordScheduleVideoQueryDto(
    Long id,
    Long tripRecordId,
    String tripRecordTitle,
    String videoUrl,
    String thumbnailUrl,
    Integer storeCount,
    Long memberId,
    String memberName
) {
}
