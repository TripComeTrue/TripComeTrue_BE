package com.haejwo.tripcometrue.domain.review.placereview.dto.response;

import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;

import java.time.LocalDateTime;

public record RegisterPlaceReviewResponseDto(
        Long placeReviewId,
        Long memberId,
        String nickname,
        String profileUrl,
        String imageUrl,
        String content,
        LocalDateTime createdAt
) {
    public static RegisterPlaceReviewResponseDto fromEntity(PlaceReview placeReview) {
        return new RegisterPlaceReviewResponseDto(
                placeReview.getId(),
                placeReview.getMember().getId(),
                placeReview.getMember().getMemberBase().getNickname(),
                placeReview.getMember().getProfile_image(),
                placeReview.getImageUrl(),
                placeReview.getContent(),
                placeReview.getCreatedAt()
        );
    }
}
