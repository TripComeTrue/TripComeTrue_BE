package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.city.entity.Weather;
import lombok.Builder;

public record WeatherResponseDto(
    Integer month,
    String maxAvgTempC,
    String minAvgTempC,
    String maxAvgTempF,
    String minAvgTempF
) {

    @Builder
    public WeatherResponseDto {
    }

    public static WeatherResponseDto fromEntity(Weather entity, String maxAvgTempF, String minAvgTempF) {
        return WeatherResponseDto.builder()
            .month(entity.getMonth())
            .maxAvgTempC(entity.getMaxAvgTemp())
            .minAvgTempC(entity.getMinAvgTemp())
            .maxAvgTempF(maxAvgTempF)
            .minAvgTempF(minAvgTempF)
            .build();
    }
}
