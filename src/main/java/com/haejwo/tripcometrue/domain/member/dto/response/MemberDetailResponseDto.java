package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;

public record MemberDetailResponseDto(
    Long id,
    String nickname,
    String profileImage,
    String introduction,
    Integer totalPoint,
    String tripLevel
) {
  public static MemberDetailResponseDto fromEntity(Member member) {
    return new MemberDetailResponseDto(
        member.getId(),
        member.getMemberBase().getNickname(),
        member.getProfileImage(),
        member.getIntroduction(),
        member.getTotalPoint(),
        member.getTripLevel().name()
    );
  }
}