package com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute;

public record TripRecordSearchParamAttribute(
    String cityName,
    String placeName,
    Long cityId,
    String totalDays
) {
}
