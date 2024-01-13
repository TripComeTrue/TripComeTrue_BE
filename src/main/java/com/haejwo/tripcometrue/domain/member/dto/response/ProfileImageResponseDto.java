package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;

public record ProfileImageResponseDto(String profileImageUrl) {
  public static ProfileImageResponseDto fromEntity(Member member) {
    return new ProfileImageResponseDto(member.getProfile_image());
  }
}
