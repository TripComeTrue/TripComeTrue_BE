package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;

public record NicknameResponseDto(String nickname) {
  public static NicknameResponseDto fromEntity(Member member) {
    return new NicknameResponseDto(member.getMemberBase().getNickname());
  }
}