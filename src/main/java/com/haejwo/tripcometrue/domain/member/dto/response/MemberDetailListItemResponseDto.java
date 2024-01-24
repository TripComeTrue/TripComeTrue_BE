package com.haejwo.tripcometrue.domain.member.dto.response;

import lombok.Builder;

public record MemberDetailListItemResponseDto(
    Long id,
    String nickname,
    String introduction,
    String profileUrl,
    Double avgRate,
    Integer tripRecordTotal,
    Integer videoTotal
) {

    @Builder
    public MemberDetailListItemResponseDto {
    }

    public static MemberDetailListItemResponseDto of(MemberListItemResponseDto dto, Integer tripRecordTotal, Integer videoTotal) {
        return MemberDetailListItemResponseDto.builder()
            .id(dto.id())
            .nickname(dto.nickname())
            .introduction(dto.introduction())
            .profileUrl(dto.profileUrl())
            .avgRate(dto.avgRate())
            .tripRecordTotal(tripRecordTotal)
            .videoTotal(videoTotal)
            .build();
    }
}
