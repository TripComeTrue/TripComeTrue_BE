package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import lombok.Builder;

public record CityPlaceWithLatLongResponseDto(
    Long placeId,
    String placeName,
    Integer storedCount,
    Integer commentTotal,
    String imageUrl,
    Double latitude,
    Double longitude
) {

    @Builder
    public CityPlaceWithLatLongResponseDto {
    }

    public static CityPlaceWithLatLongResponseDto fromEntity(Place entity, String imageUrl) {
        return CityPlaceWithLatLongResponseDto.builder()
            .placeId(entity.getId())
            .placeName(entity.getName())
            .storedCount(entity.getStoredCount())
            .commentTotal(0) // TODO: 댓글 수 적용
            .imageUrl(imageUrl)
            .latitude(entity.getLatitude())
            .longitude(entity.getLongitude())
            .build();
    }
}
