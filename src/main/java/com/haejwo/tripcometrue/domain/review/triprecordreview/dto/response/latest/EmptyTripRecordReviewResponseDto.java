package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest;

public record EmptyTripRecordReviewResponseDto(

        Long totalCount,
        Long myTripRecordReviewId,
        Float myRatingScore

) implements LatestReviewResponseDto {
    public static EmptyTripRecordReviewResponseDto fromData(Long totalCount, Long myTripRecordReviewId, Float myRatingScore) {
        return new EmptyTripRecordReviewResponseDto(totalCount, myTripRecordReviewId, myRatingScore);
    }
}
