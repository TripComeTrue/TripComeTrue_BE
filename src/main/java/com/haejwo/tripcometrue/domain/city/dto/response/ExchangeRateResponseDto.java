package com.haejwo.tripcometrue.domain.city.dto.response;

import lombok.Builder;

public record ExchangeRateResponseDto(
    String curUnit,
    String curName,
    String exchangeRate,
    String country
) {

    @Builder
    public ExchangeRateResponseDto {
    }
}
