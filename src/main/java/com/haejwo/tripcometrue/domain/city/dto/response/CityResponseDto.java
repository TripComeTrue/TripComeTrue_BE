package com.haejwo.tripcometrue.domain.city.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haejwo.tripcometrue.domain.city.entity.City;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CityResponseDto(
    Long id,
    String name,
    String language,
    String timeDifference,
    String voltage,
    String visa,
    String currency,
    String weatherRecommendation,
    String weatherDescription,
    String country
) {

    @Builder
    public CityResponseDto {
    }

    public static CityResponseDto fromEntity(City entity) {
        return CityResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .language(entity.getLanguage())
            .timeDifference(entity.getTimeDifference())
            .voltage(entity.getVoltage())
            .visa(entity.getVisa())
            .currency(entity.getCurrency().name())
            .weatherRecommendation(entity.getWeatherRecommendation())
            .weatherDescription(entity.getWeatherDescription())
            .country(entity.getCountry().getDescription())
            .build();
    }
}
