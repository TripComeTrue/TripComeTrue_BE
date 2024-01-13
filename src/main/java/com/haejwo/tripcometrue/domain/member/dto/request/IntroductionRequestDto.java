package com.haejwo.tripcometrue.domain.member.dto.request;

import jakarta.validation.constraints.Size;

public record IntroductionRequestDto(
    @Size(max = 20, message = "소개는 20자 내로 작성해주세요.")
    String introduction)
{ }
