package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;

public record IntroductionResponseDto(String introduction) {
  public static IntroductionResponseDto fromEntity(Member member) {
    return new IntroductionResponseDto(member.getIntroduction());
  }
}