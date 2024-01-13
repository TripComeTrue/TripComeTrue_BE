package com.haejwo.tripcometrue.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordCheckRequestDto(
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    String currentPassword

) {}