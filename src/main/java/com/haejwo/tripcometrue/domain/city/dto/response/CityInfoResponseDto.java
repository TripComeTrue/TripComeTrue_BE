package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.city.entity.City;
import lombok.Builder;

public record CityInfoResponseDto(
    Long id,
    String name,
    String language,
    String timeDifference,
    String voltage,
    String visa,
    String curUnit,
    String curName
) {

    @Builder
    public CityInfoResponseDto {
    }

    public static CityInfoResponseDto fromEntity(City entity) {
        return CityInfoResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .language(entity.getLanguage())
            .timeDifference(entity.getTimeDifference())
            .voltage(entity.getVoltage())
            .visa(entity.getVisa())
            .curUnit(entity.getCurrency().toString())
            .curName(entity.getCurrency().getCurrencyName())
            .build();
    }
}
