package com.haejwo.tripcometrue.domain.review.placereview.dto.response;

import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import org.springframework.data.domain.Page;

import java.util.List;

public record PlaceReviewListResponseDto(

        Long totalCount,
        int nowPageNumber,
        boolean isFirst,
        boolean isLast,
        List<PlaceReviewResponseDto> placeReviews

) {
    public static PlaceReviewListResponseDto fromResponseDtos(
            Page<PlaceReview> reviews,
            List<PlaceReviewResponseDto> placeReviews
    ) {
        return new PlaceReviewListResponseDto(
                reviews.getTotalElements(),
                reviews.getNumber(),
                reviews.isFirst(),
                reviews.isLast(),
                placeReviews
        );
    }
}