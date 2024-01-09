package com.haejwo.tripcometrue.domain.triprecord.dto.response.member;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import lombok.Builder;

public record TripRecordMemberResponseDto(
    String nickname,
    String profile_image
) {

    @Builder
    public TripRecordMemberResponseDto(String nickname, String profile_image) {
        this.nickname = nickname;
        this.profile_image = profile_image;
    }

    public static TripRecordMemberResponseDto formEntity(Member entity) {
        return TripRecordMemberResponseDto.builder()
            .nickname(entity.getMemberBase().getNickname())
            .nickname(entity.getProfile_image())
            .build();
    }

}
