package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import lombok.Builder;

public record MemberListItemResponseDto(
    Long memberId,
    String nickname,
    String introduction,
    String profileImageUrl,
    Double averageRating
) {

    @Builder
    public MemberListItemResponseDto {
    }

    public static MemberListItemResponseDto fromEntity(Member entity) {
        return MemberListItemResponseDto.builder()
            .memberId(entity.getId())
            .nickname(entity.getMemberBase().getNickname())
            .introduction(entity.getIntroduction())
            .profileImageUrl(entity.getProfileImage())
            .averageRating(entity.getMemberRating())
            .build();
    }
}
