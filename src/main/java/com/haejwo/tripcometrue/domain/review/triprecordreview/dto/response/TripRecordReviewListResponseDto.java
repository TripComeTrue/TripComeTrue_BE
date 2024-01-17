package com.haejwo.tripcometrue.domain.review.triprecordreview.response;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import java.time.LocalDateTime;

public record TripRecordReviewListResponseDto(
    Long id,
    String content,
    Short rating,
    Integer likeCount,
    String imageUrl,
    Long memberId,
    Long tripRecordId,
    LocalDateTime createdAt
) {
  public static TripRecordReviewListResponseDto fromEntity(TripRecordReview entity) {
    return new TripRecordReviewListResponseDto(
        entity.getId(),
        entity.getContent(),
        entity.getRating(),
        entity.getLikeCount(),
        entity.getImageUrl(),
        entity.getMember().getId(),
        entity.getTripRecord().getId(),
        entity.getCreatedAt()
    );
  }
}