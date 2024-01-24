package com.haejwo.tripcometrue.domain.comment.placereview.dto.response;

import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Objects;

public record PlaceReviewCommentListResponseDto(

        int totalCount,
        List<PlaceReviewCommentResponseDto> comments

) {

    public static PlaceReviewCommentListResponseDto fromData(int totalCount, Slice<PlaceReviewComment> placeReviewComments, Member loginMember) {
        return new PlaceReviewCommentListResponseDto(
                totalCount,
                placeReviewComments.map(placeReviewComment -> {
                            if (placeReviewComment.getParentComment() == null) { //최상위 댓글만 포함
                                return PlaceReviewCommentResponseDto.fromEntity(placeReviewComment, loginMember);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
}
