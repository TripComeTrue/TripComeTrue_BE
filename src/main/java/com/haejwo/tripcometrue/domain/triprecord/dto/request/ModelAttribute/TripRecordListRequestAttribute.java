package com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute;

import com.querydsl.core.types.Order;

public record TripRecordListRequestAttribute(
    String hashtag,
    Integer expenseRangeType,
    Integer totalDays,
    Long placeId,
    String orderBy,
    Order order
) {

}
