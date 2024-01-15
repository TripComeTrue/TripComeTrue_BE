package com.haejwo.tripcometrue.domain.place.dto.response;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import lombok.Builder;

public record PlaceMapInfoResponseDto(
    Long placeId,
    String placeName,
    Double latitude,
    Double longitude,
    Integer storeNumber,
    Integer commentNumber


) {
    @Builder
    public PlaceMapInfoResponseDto(Long placeId, String placeName, Double latitude,
        Double longitude,
        Integer storeNumber, Integer commentNumber) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeNumber = storeNumber;
        this.commentNumber = commentNumber;
    }

    public static PlaceMapInfoResponseDto fromEntity(Place entity) {
        return PlaceMapInfoResponseDto.builder()
            .placeId(entity.getId())
            .placeName(entity.getName())
            .latitude(entity.getLatitude())
            .longitude(entity.getLongitude())
//            .storeNumber()
//            .commentNumber()
            .build();
    }





}
