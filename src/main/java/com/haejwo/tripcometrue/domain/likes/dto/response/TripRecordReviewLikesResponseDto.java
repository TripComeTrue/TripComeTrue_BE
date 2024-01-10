package com.haejwo.tripcometrue.domain.likes.dto.response;
import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import lombok.Builder;

@Builder
public record TripRecordReviewLikesResponseDto(
    Long likeId,
    Long memberId,
    Long tripRecordReviewId
) {

  public static TripRecordReviewLikesResponseDto fromEntity(
      TripRecordReviewLikes tripRecordReviewLikes) {
    return new TripRecordReviewLikesResponseDto(
        tripRecordReviewLikes.getId(),
        tripRecordReviewLikes.getMember().getId(),
        tripRecordReviewLikes.getTripRecordReview().getId());
  }
}
