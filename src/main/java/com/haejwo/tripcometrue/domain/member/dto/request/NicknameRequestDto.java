package com.haejwo.tripcometrue.domain.member.dto.request;

import com.haejwo.tripcometrue.domain.member.dto.response.NicknameResponseDto;
import com.haejwo.tripcometrue.domain.member.entity.Member;

public record NicknameRequestDto(String nickname) {
  public static NicknameResponseDto fromEntity(Member member) {
    return new NicknameResponseDto(member.getMemberBase().getNickname());
  }
}
