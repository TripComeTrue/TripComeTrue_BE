package com.haejwo.tripcometrue.domain.member.dto.response;

import lombok.Builder;

public record MemberDetailListItemResponseDto(
    Long memberId,
    String nickname,
    String introduction,
    String profileImageUrl,
    Double averageRating,
    Integer tripRecordTotal,
    Integer videoTotal
) {

    @Builder
    public MemberDetailListItemResponseDto {
    }

    public static MemberDetailListItemResponseDto of(MemberListItemResponseDto dto, Integer tripRecordTotal, Integer videoTotal) {
        return MemberDetailListItemResponseDto.builder()
            .memberId(dto.memberId())
            .nickname(dto.nickname())
            .introduction(dto.introduction())
            .profileImageUrl(dto.profileImageUrl())
            .averageRating(dto.averageRating())
            .tripRecordTotal(tripRecordTotal)
            .videoTotal(videoTotal)
            .build();
    }
}
