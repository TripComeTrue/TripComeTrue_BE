package com.haejwo.tripcometrue.domain.comment.triprecord.dto.response;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Objects;

public record TripRecordCommentListResponseDto(

        int totalCount,
        List<TripRecordCommentResponseDto> comments

) {

    public static TripRecordCommentListResponseDto fromData(int totalCount, Slice<TripRecordComment> tripRecordComments, Member loginMember) {
        return new TripRecordCommentListResponseDto(
                totalCount,
                tripRecordComments.map(tripRecordComment -> {
                            if (tripRecordComment.getParentComment() == null) { //최상위 댓글만 포함
                                return TripRecordCommentResponseDto.fromEntity(tripRecordComment, loginMember);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
}
