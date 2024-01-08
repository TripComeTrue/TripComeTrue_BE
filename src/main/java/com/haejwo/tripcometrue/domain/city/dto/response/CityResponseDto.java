package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.city.entity.City;

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
    public static CityResponseDto fromEntity(City entity) {
        return new CityResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getLanguage(),
            entity.getTimeDifference(),
            entity.getVoltage(),
            entity.getVisa(),
            entity.getCurrency(),
            entity.getWeatherRecommendation(),
            entity.getWeatherDescription(),
            entity.getCountry().getDescription()
        );
    }
}
