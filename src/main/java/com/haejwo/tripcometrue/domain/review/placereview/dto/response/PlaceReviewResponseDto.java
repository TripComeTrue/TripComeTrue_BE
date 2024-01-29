package com.haejwo.tripcometrue.domain.review.placereview.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.comment.placereview.dto.response.PlaceReviewCommentResponseDto;
import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        Integer commentCount,
        List<PlaceReviewCommentResponseDto> comments

) {

    public static PlaceReviewResponseDto fromEntityWithComment(
            PlaceReview placeReview,
            boolean amILike,
            Slice<PlaceReviewComment> placeReviewComments,
            Member member
    ) {

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
                placeReview.getCommentCount(),
                placeReviewComments.map(placeReviewComment -> {
                            if (placeReviewComment.getParentComment() == null) {
                                return PlaceReviewCommentResponseDto.fromEntity(placeReviewComment, member);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    public static PlaceReviewResponseDto fromEntity(
            PlaceReview placeReview,
            boolean amILike
    ) {

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
                placeReview.getCommentCount(),
                null
        );
    }
}
