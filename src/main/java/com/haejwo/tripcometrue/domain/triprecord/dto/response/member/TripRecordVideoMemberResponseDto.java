package com.haejwo.tripcometrue.domain.triprecord.dto.response.member;

import lombok.Builder;

public record TripRecordVideoMemberResponseDto(
    Long memberId,
    String nickname,
    String profileImage
) {

    @Builder
    public TripRecordVideoMemberResponseDto(Long memberId, String nickname, String profileImage) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

}
