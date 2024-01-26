package com.haejwo.tripcometrue.domain.city.dto.request;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.CurrencyUnit;
import com.haejwo.tripcometrue.global.enums.Country;

public record AddCityRequestDto(
    String name,
    String language,
    String timeDifference,
    String voltage,
    String visa,
    String currency,
    String weatherRecommendation,
    String weatherDescription,
    Country country
) {
    public City toEntity() {
        return City.builder()
            .name(this.name)
            .language(this.language)
            .timeDifference(this.timeDifference)
            .voltage(this.voltage)
            .visa(this.visa)
            .currency(CurrencyUnit.valueOf(this.currency))
            .weatherRecommendation(this.weatherRecommendation)
            .weatherDescription(this.weatherDescription)
            .country(this.country)
            .build();
    }
}
