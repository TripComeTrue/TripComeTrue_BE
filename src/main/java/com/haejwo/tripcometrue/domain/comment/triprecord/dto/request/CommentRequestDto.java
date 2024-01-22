package com.haejwo.tripcometrue.domain.comment.triprecord.dto.request;

import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CommentRequestDto(

        @NotNull(message = "본문은 필수로 입력해야 합니다.")
        @Length(min = 1, max = 500, message = "작성 허용 범위는 최소 1자 또는 최대 500자 입니다.")
        String content

) {

    public static TripRecordComment toEntity(Member member, TripRecord tripRecord, CommentRequestDto requestDto) {
        return TripRecordComment.builder()
                .member(member)
                .tripRecord(tripRecord)
                .content(requestDto.content)
                .build();
    }
}
