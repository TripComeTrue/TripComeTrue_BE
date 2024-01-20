package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import org.springframework.data.domain.Page;

import java.util.List;

public record TripRecordReviewListResponseDto(

        Long totalCount,
        int nowPageNumber,
        boolean isFirst,
        boolean isLast,
        List<TripRecordReviewResponseDto> tripRecordReviews

) {
    public static TripRecordReviewListResponseDto fromResponseDtos(
            Page<TripRecordReview> reviews,
            List<TripRecordReviewResponseDto> tripRecordReviews
    ) {
        return new TripRecordReviewListResponseDto(
                reviews.getTotalElements(),
                reviews.getNumber(),
                reviews.isFirst(),
                reviews.isLast(),
                tripRecordReviews
        );
    }
}