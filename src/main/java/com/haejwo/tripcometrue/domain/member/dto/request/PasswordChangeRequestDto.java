package com.haejwo.tripcometrue.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequestDto(

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    String newPassword

) {}