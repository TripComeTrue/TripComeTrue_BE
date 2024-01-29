package com.haejwo.tripcometrue.domain.comment.placereview.dto.request;

import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record PlaceReviewCommentRequestDto(

        @NotNull(message = "본문은 필수로 입력해야 합니다.")
        @Length(min = 1, max = 500, message = "작성 허용 범위는 최소 1자 또는 최대 500자 입니다.")
        String content

) {

    public static PlaceReviewComment toComment(Member member, PlaceReview placeReview, PlaceReviewCommentRequestDto requestDto) {
        return PlaceReviewComment.builder()
                .member(member)
                .placeReview(placeReview)
                .content(requestDto.content)
                .build();
    }

    public static PlaceReviewComment toReplyComment(
            Member member,
            PlaceReview placeReview,
            PlaceReviewComment placeReviewComment,
            PlaceReviewCommentRequestDto requestDto
    ) {

        return PlaceReviewComment.builder()
                .member(member)
                .placeReview(placeReview)
                .parentComment(placeReviewComment)
                .content(requestDto.content)
                .build();
    }
}
