package com.haejwo.tripcometrue.domain.likes.dto.response;

import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import lombok.Builder;


@Builder
public record PlaceReviewLikesResponseDto(
    Long likeId,
    Long memberId,
    Long placeReviewId
) {

  public static PlaceReviewLikesResponseDto fromEntity(PlaceReviewLikes placeReviewLikes) {
    return new PlaceReviewLikesResponseDto(
        placeReviewLikes.getId(),
        placeReviewLikes.getMember().getId(),
        placeReviewLikes.getPlaceReview().getId());
  }
}