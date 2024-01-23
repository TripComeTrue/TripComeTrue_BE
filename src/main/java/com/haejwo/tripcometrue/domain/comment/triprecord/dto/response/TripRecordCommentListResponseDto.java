package com.haejwo.tripcometrue.domain.comment.triprecord.dto.response;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;

public record TripRecordCommentListResponseDto(

        Long totalCount,
        List<TripRecordCommentResponseDto> comments

) {

    public static TripRecordCommentListResponseDto fromData(Page<TripRecordComment> page, Member loginMember) {
        return new TripRecordCommentListResponseDto(
                page.getTotalElements(),
                page.map(tripRecordComment -> {
                            if (tripRecordComment.getParentComment() == null) {
                                return TripRecordCommentResponseDto.fromEntity(tripRecordComment, loginMember);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
}
