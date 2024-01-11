package com.haejwo.tripcometrue.domain.triprecord.dto;

public record TripRecordListRequestAttribute(
    String hashtag,
    Integer expenseRangeType,
    Integer totalDays,
    Long placeId
) {

}
