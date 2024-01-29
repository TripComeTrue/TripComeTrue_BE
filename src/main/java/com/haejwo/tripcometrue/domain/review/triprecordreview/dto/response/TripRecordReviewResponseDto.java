package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;

import java.time.LocalDateTime;

public record TripRecordReviewResponseDto(

        Long tripRecordId,
        String tripRecordTitle,
        Long tripRecordReviewId,
        String imageUrl,
        Long memberId,
        String nickname,
        Float ratingScore,
        String content,
        Integer likeCount,
        boolean amILike,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
        LocalDateTime createdAt

) {
    public static TripRecordReviewResponseDto fromEntity(TripRecordReview tripRecordReview, boolean amILike) {
        return new TripRecordReviewResponseDto(
                tripRecordReview.getTripRecord().getId(),
                tripRecordReview.getTripRecord().getTitle(),
                tripRecordReview.getId(),
                tripRecordReview.getImageUrl(),
                tripRecordReview.getMember().getId(),
                tripRecordReview.getMember().getMemberBase().getNickname(),
                tripRecordReview.getRatingScore(),
                tripRecordReview.getContent(),
                tripRecordReview.getLikeCount(),
                amILike,
                tripRecordReview.getCreatedAt()
        );
    }
}
