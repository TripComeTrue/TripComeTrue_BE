package com.haejwo.tripcometrue.domain.review.placereview.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;

import java.time.LocalDateTime;

public record PlaceReviewResponseDto(

        Long placeReviewId,
        Long memberId,
        String nickname,
        String profileUrl,
        String imageUrl,
        String content,
        Integer likeCount,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
        LocalDateTime createdAt,

        boolean amILike,
        Integer commentCount

) {
        public static PlaceReviewResponseDto fromEntity(PlaceReview placeReview, boolean amILike) {
                return new PlaceReviewResponseDto(
                        placeReview.getId(),
                        placeReview.getMember().getId(),
                        placeReview.getMember().getMemberBase().getNickname(),
                        placeReview.getMember().getProfileImage(),
                        placeReview.getImageUrl(),
                        placeReview.getContent(),
                        placeReview.getLikeCount(),
                        placeReview.getCreatedAt(),
                        amILike,
                        placeReview.getCommentCount()
                );
        }
}
