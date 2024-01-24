package com.haejwo.tripcometrue.domain.member.dto.response;

import lombok.Builder;

public record MemberDetailListItemResponseDto(
    Long memberId,
    String nickname,
    String introduction,
    String profileUrl,
    Double averageRating,
    Integer tripRecordTotal,
    Integer videoTotal
) {

    @Builder
    public MemberDetailListItemResponseDto {
    }

    public static MemberDetailListItemResponseDto of(MemberListItemResponseDto dto, Integer tripRecordTotal, Integer videoTotal) {
        return MemberDetailListItemResponseDto.builder()
            .memberId(dto.id())
            .nickname(dto.nickname())
            .introduction(dto.introduction())
            .profileUrl(dto.profileUrl())
            .averageRating(dto.avgRate())
            .tripRecordTotal(tripRecordTotal)
            .videoTotal(videoTotal)
            .build();
    }
}
