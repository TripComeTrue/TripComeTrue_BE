package com.haejwo.tripcometrue.domain.store.dto.response;

import lombok.Builder;

public record CheckCityStoredResponseDto(
    boolean isStored
) {

    @Builder
    public CheckCityStoredResponseDto {
    }
}
