package com.haejwo.tripcometrue.domain.member.response;


import com.haejwo.tripcometrue.domain.member.entity.Member;

public record SignUpResponse(

    Long memberId,
    String email,
    String name

) {
    public static SignUpResponse fromEntity(Member member) {
        return new SignUpResponse (
            member.getMemberId(),
            member.getMemberBase().getEmail(),
            member.getMemberBase().getNickname()
        );
    }
}
