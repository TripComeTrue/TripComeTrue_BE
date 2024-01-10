package com.haejwo.tripcometrue.domain.review.triprecordreview.response;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;

public record TripRecordReviewEvaluateResponseDto(
        Long id,
        short rating

) {
    public static TripRecordReviewEvaluateResponseDto fromEntity(TripRecordReview tripRecordReview) {
        return new TripRecordReviewEvaluateResponseDto(
                tripRecordReview.getId(),
                tripRecordReview.getRating()
        );
    }
}
