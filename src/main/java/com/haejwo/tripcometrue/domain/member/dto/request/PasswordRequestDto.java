package com.haejwo.tripcometrue.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordRequestDto(
    String currentPassword,

    String newPassword,

    String confirmPassword
) {}