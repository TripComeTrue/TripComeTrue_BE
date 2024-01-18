package com.haejwo.tripcometrue.domain.review.placereview.dto.response;

import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;

import java.time.LocalDateTime;

public record PlaceReviewListResponseDto(

    Long id,
    String content,
    String imageUrl,
    Integer likeCount,
    Long memberId,
    Long placeId,
    LocalDateTime createdAt

) {
  public static PlaceReviewListResponseDto fromEntity(PlaceReview placeReview) {
    return new PlaceReviewListResponseDto(
        placeReview.getId(),
        placeReview.getContent(),
        placeReview.getImageUrl(),
        placeReview.getLikeCount(),
        placeReview.getMember().getId(),
        placeReview.getPlace().getId(),
        placeReview.getCreatedAt()
    );
  }
}