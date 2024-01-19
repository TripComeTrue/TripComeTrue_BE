package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest;

import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;

public record LatestTripRecordReviewResponseDto(

        Long totalCount,
        TripRecordReviewResponseDto latestTripRecordReview,
        Float myRatingScore

) implements LatestReviewResponseDto {
    public static LatestTripRecordReviewResponseDto fromEntity(Long totalCount, TripRecordReview tripRecordReview, Float myRatingScore) {
        return new LatestTripRecordReviewResponseDto(
                totalCount,
                TripRecordReviewResponseDto.fromEntity(tripRecordReview, false),
                myRatingScore);
    }
}
