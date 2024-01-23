package com.haejwo.tripcometrue.domain.comment.triprecord.dto.response;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public record TripRecordCommentListResponseDto(

        Long totalCount,
        List<TripRecordCommentResponseDto> comments

) {

    public static TripRecordCommentListResponseDto fromData(Page<TripRecordComment> page, Member loginMember) {
        return new TripRecordCommentListResponseDto(
                page.getTotalElements(),
                page.map(tripRecordComment ->
                                TripRecordCommentResponseDto.fromEntity(tripRecordComment, loginMember))
                        .toList()
        );
    }
}
