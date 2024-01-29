package com.haejwo.tripcometrue.domain.member.dto.response;


import com.haejwo.tripcometrue.domain.member.entity.Member;

public record SignUpResponseDto(

    Long memberId,
    String email,
    String name

) {

    public static SignUpResponseDto fromEntity(Member member) {
        return new SignUpResponseDto(
            member.getId(),
            member.getMemberBase().getEmail(),
            member.getMemberBase().getNickname()
        );
    }
}
