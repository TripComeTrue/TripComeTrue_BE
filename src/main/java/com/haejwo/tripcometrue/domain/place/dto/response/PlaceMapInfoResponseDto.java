package com.haejwo.tripcometrue.domain.place.dto.response;

import lombok.Builder;

public record PlaceMapInfoResponseDto(
    Long placeId,
    String placeName,
    Double latitude,
    Double longitude,
    Integer storeCount,
    Integer commentCount,
    String imageUrl


) {

    @Builder
    public PlaceMapInfoResponseDto(Long placeId, String placeName, Double latitude,
        Double longitude,
        Integer storeCount, Integer commentCount, String imageUrl) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeCount = storeCount;
        this.commentCount = commentCount;
        this.imageUrl = imageUrl;
    }


//    public static PlaceMapInfoResponseDto fromEntity(Place entity) {
//        return PlaceMapInfoResponseDto.builder()
//            .placeId(entity.getId())
//            .placeName(entity.getName())
//            .latitude(entity.getLatitude())
//            .longitude(entity.getLongitude())
//            .storeCount()
//            .commentCount()
//            .build();
//    }





}
