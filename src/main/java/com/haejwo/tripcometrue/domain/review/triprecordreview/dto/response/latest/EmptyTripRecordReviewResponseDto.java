package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest;

public record EmptyTripRecordReviewResponseDto(

        Long totalCount,
        Float myRatingScore

) implements LatestReviewResponseDto {
    public static EmptyTripRecordReviewResponseDto fromData(Long totalCount, Float myRatingScore) {
        return new EmptyTripRecordReviewResponseDto(totalCount, myRatingScore);
    }
}
