package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import lombok.Builder;

public record MemberListItemResponseDto(
    Long id,
    String nickname,
    String introduction,
    String profileUrl,
    Double avgRate
) {

    @Builder
    public MemberListItemResponseDto {
    }

    public static MemberListItemResponseDto fromEntity(Member entity) {
        return MemberListItemResponseDto.builder()
            .id(entity.getId())
            .nickname(entity.getMemberBase().getNickname())
            .introduction(entity.getIntroduction())
            .profileUrl(entity.getProfileImage())
            .avgRate(entity.getMemberRating())
            .build();
    }
}
