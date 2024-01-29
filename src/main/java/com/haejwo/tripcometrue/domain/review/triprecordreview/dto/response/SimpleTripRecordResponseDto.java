package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;

public record SimpleTripRecordResponseDto(

        String tripRecordTitle,
        String imageUrl,
        String content,
        Float ratingScore

) {

    public static SimpleTripRecordResponseDto fromEntity(String title, TripRecordReview tripRecordReview) {
        return new SimpleTripRecordResponseDto(
                title,
                tripRecordReview.getImageUrl(),
                tripRecordReview.getContent(),
                tripRecordReview.getRatingScore()
        );
    }
}
