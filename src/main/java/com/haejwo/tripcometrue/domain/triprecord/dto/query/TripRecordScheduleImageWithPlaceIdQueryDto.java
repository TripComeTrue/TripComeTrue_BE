package com.haejwo.tripcometrue.domain.triprecord.dto.query;

import java.time.LocalDateTime;

public record TripRecordScheduleImageWithPlaceIdQueryDto(
    Long placeId,
    String imageUrl,
    LocalDateTime createdAt
) {
}
