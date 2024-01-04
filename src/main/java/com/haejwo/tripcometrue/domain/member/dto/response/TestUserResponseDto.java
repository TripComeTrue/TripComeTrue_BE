package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.member.entity.Member;

public record TestUserResponseDto(

    String email,
    String nickname,
    String authority,
    String provider

) {

    public static TestUserResponseDto fromEntity(Member member) {
        return new TestUserResponseDto(
            member.getMemberBase().getEmail(),
            member.getMemberBase().getNickname(),
            member.getMemberBase().getAuthority(),
            member.getProvider()
        );
    }
}
