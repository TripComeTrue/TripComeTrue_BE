package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import lombok.Builder;

public record CityPlaceResponseDto(
    Long placeId,
    String placeName,
    Integer storedCount,
    Integer commentTotal,
    String imageUrl
) {

    @Builder
    public CityPlaceResponseDto {
    }

    public static CityPlaceResponseDto fromEntity(Place entity, String imageUrl) {
        return CityPlaceResponseDto.builder()
            .placeId(entity.getId())
            .placeName(entity.getName())
            .storedCount(entity.getStoredCount())
            .commentTotal(entity.getCommentCount())
            .imageUrl(imageUrl)
            .build();
    }
}
