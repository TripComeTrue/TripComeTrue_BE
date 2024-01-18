package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.city.entity.City;
import lombok.Builder;

public record GetCountryCityResponseDto(
    Long cityId,
    String cityName,
    String cityImageUrl

) {

    @Builder
    public GetCountryCityResponseDto(Long cityId, String cityName, String cityImageUrl) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityImageUrl = cityImageUrl;
    }

    public static GetCountryCityResponseDto fromEntity(City city) {
        return GetCountryCityResponseDto.builder()
            .cityId(city.getId())
            .cityName(city.getName())
            .cityImageUrl(city.getImageUrl())
            .build();
    }
}
