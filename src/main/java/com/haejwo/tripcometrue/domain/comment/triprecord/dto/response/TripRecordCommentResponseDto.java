package com.haejwo.tripcometrue.domain.comment.triprecord.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.comment.triprecord.entity.TripRecordComment;
import com.haejwo.tripcometrue.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public record TripRecordCommentResponseDto(

        Long commentId,
        Long memberId,
        String profileUrl,
        String nickname,
        boolean isWriter,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
        LocalDateTime createdAt,

        List<TripRecordCommentResponseDto> replyComments

) {

    public static TripRecordCommentResponseDto fromEntity(TripRecordComment tripRecordComment, Member loginMember) {


            return new TripRecordCommentResponseDto(
                    tripRecordComment.getId(),
                    tripRecordComment.getMember().getId(),
                    tripRecordComment.getMember().getProfile_image(),
                    tripRecordComment.getMember().getMemberBase().getNickname(),
                    isWriter(tripRecordComment, loginMember),
                    tripRecordComment.getCreatedAt(),
                    getReplyComments(tripRecordComment, loginMember)
            );

    }

    private static boolean isWriter(TripRecordComment tripRecordComment, Member loginMember) {
        return Objects.equals(tripRecordComment.getMember(), loginMember);
    }

    private static List<TripRecordCommentResponseDto> getReplyComments(TripRecordComment tripRecordComment, Member loginMember) {
        if (tripRecordComment.getParentComment() == null) {
            return tripRecordComment.getChildComments().stream()
                    .map(comment -> TripRecordCommentResponseDto.fromEntity(comment, loginMember))
                    .sorted(Comparator.comparing(TripRecordCommentResponseDto::createdAt).reversed())
                    .toList();
        }
        return null;
    }
}
