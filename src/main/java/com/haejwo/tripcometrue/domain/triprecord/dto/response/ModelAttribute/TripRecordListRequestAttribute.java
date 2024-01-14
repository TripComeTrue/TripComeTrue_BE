package com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute;

public record TripRecordListRequestAttribute(
    String hashtag,
    Integer expenseRangeType,
    Integer totalDays,
    Long placeId
) {

}
