package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.city.entity.City;
import lombok.Builder;

public record TopCityResponseDto(
    Long cityId,
    String cityName,
    Integer storedCount,
    String imageUrl
) {

    @Builder
    public TopCityResponseDto {
    }

    public static TopCityResponseDto fromEntity(City entity) {
        return TopCityResponseDto.builder()
            .cityId(entity.getId())
            .cityName(entity.getName())
            .storedCount(entity.getStoreCount())
            .imageUrl(entity.getImageUrl())
            .build();
    }
}
