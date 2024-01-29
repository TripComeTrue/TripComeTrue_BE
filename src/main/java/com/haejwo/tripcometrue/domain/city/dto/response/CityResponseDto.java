package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.city.entity.City;
import lombok.Builder;

import java.util.Objects;

public record CityResponseDto(
    Long cityId,
    String name,
    String englishName,
    String language,
    String timeDifference,
    String voltage,
    String visa,
    String curUnit,
    String exchangeRate,
    String weatherRecommendation,
    String weatherDescription,
    String country,
    String imageUrl
) {

    @Builder
    public CityResponseDto {
    }

    public static CityResponseDto fromEntity(City entity) {
        return CityResponseDto.builder()
            .cityId(entity.getId())
            .name(entity.getName())
            .englishName(entity.getEnglishName())
            .language(entity.getLanguage())
            .timeDifference(entity.getTimeDifference())
            .voltage(entity.getVoltage())
            .visa(entity.getVisa())
            .curUnit(
                Objects.nonNull(entity.getCurrency()) ? entity.getCurrency().name() : null
            )
            .exchangeRate(null)
            .weatherRecommendation(entity.getWeatherRecommendation())
            .weatherDescription(entity.getWeatherDescription())
            .country(entity.getCountry().getDescription())
            .imageUrl(entity.getImageUrl())
            .build();
    }

    public static CityResponseDto fromEntity(City entity, String exchangeRate) {
        return CityResponseDto.builder()
            .cityId(entity.getId())
            .name(entity.getName())
            .englishName(entity.getEnglishName())
            .language(entity.getLanguage())
            .timeDifference(entity.getTimeDifference())
            .voltage(entity.getVoltage())
            .visa(entity.getVisa())
            .curUnit(
                Objects.nonNull(entity.getCurrency()) ? entity.getCurrency().name() : null
            )
            .exchangeRate(exchangeRate)
            .weatherRecommendation(entity.getWeatherRecommendation())
            .weatherDescription(entity.getWeatherDescription())
            .country(entity.getCountry().getDescription())
            .imageUrl(entity.getImageUrl())
            .build();
    }
}
