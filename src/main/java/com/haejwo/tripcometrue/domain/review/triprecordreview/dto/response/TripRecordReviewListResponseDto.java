package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response;

import java.util.List;

public record TripRecordReviewListResponseDto(

        Long totalCount,
        List<TripRecordReviewResponseDto> myTripRecordReviews

) {
    public static TripRecordReviewListResponseDto fromResponseDtos(
            Long totalCount,
            List<TripRecordReviewResponseDto> tripRecordReviews
    ) {
        return new TripRecordReviewListResponseDto(totalCount, tripRecordReviews);
    }
}