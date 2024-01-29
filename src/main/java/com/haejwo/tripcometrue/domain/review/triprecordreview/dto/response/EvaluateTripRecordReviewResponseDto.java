package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;

public record EvaluateTripRecordReviewResponseDto(

        Long tripRecordReviewId,
        Float ratingScore

) {
    public static EvaluateTripRecordReviewResponseDto fromEntity(TripRecordReview tripRecordReview) {
        return new EvaluateTripRecordReviewResponseDto(
                tripRecordReview.getId(),
                tripRecordReview.getRatingScore()
        );
    }
}
