package com.haejwo.tripcometrue.domain.triprecord.dto.response.member;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import lombok.Builder;

public record TripRecordMemberResponseDto(
    String nickname,
    String profileImage
) {

    @Builder
    public TripRecordMemberResponseDto(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static TripRecordMemberResponseDto fromEntity(Member entity) {
        return TripRecordMemberResponseDto.builder()
            .nickname(entity.getMemberBase().getNickname())
            .profileImage(entity.getProfileImage())
            .build();
    }

}
