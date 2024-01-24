package com.haejwo.tripcometrue.domain.place.dto.response;

import lombok.Builder;

public record PlaceNearbyResponseDto(
    Long placeId,
    String placeName,
    String imageUrl,
    Double latitude,
    Double longitude,
    Integer storedCount,
    Integer reviewCount,
    Integer commentCount
) {

    @Builder
    public PlaceNearbyResponseDto(Long placeId, String placeName, String imageUrl, Double latitude,
        Double longitude, Integer storedCount, Integer reviewCount, Integer commentCount) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storedCount = storedCount;
        this.reviewCount = reviewCount;
        this.commentCount = commentCount;
    }
}
