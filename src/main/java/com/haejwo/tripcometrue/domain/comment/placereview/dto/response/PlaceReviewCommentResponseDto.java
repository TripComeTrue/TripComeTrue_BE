package com.haejwo.tripcometrue.domain.comment.placereview.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public record PlaceReviewCommentResponseDto(

        Long commentId,
        Long memberId,
        String profileUrl,
        String nickname,
        boolean isWriter,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
        LocalDateTime createdAt,

        List<PlaceReviewCommentResponseDto> replyComments

) {

    public static PlaceReviewCommentResponseDto fromEntity(PlaceReviewComment placeReviewComment, Member loginMember) {
        return new PlaceReviewCommentResponseDto(
                placeReviewComment.getId(),
                placeReviewComment.getMember().getId(),
                placeReviewComment.getMember().getProfileImage(),
                placeReviewComment.getMember().getMemberBase().getNickname(),
                isWriter(placeReviewComment, loginMember),
                placeReviewComment.getCreatedAt(),
                getReplyComments(placeReviewComment, loginMember) //자식 댓글 리스트에 담기
        );
    }

    private static boolean isWriter(PlaceReviewComment placeReviewComment, Member loginMember) {
        return Objects.equals(placeReviewComment.getMember(), loginMember);
    }

    private static List<PlaceReviewCommentResponseDto> getReplyComments(PlaceReviewComment placeReviewComment, Member loginMember) {
        if (placeReviewComment.getParentComment() == null) {
            return placeReviewComment.getChildComments().stream()
                    .map(comment -> PlaceReviewCommentResponseDto.fromEntity(comment, loginMember))
                    .sorted(Comparator.comparing(PlaceReviewCommentResponseDto::createdAt).reversed())
                    .toList();
        }
        return null;
    }
}
