package com.haejwo.tripcometrue.domain.store.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;
import java.time.LocalTime;

public record PlaceStoreResponseDto(
    Long id,
    String name,
    String address,
    String description,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss")
    LocalTime weekdayOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss")
    LocalTime weekdayCloseTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss")
    LocalTime weekendOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss")
    LocalTime weekendCloseTime,
    Integer storedCount,
    Double latitude,
    Double longtitude,
    Integer commentCount,
    Long cityId,
    String imageUrl
) {

  public static PlaceStoreResponseDto fromEntity(PlaceStore placeStore, String imageUrl) {
    Place place = placeStore.getPlace();
    return new PlaceStoreResponseDto(
        place.getId(),
        place.getName(),
        place.getAddress(),
        place.getDescription(),
        place.getWeekdayOpenTime(),
        place.getWeekdayCloseTime(),
        place.getWeekendOpenTime(),
        place.getWeekendCloseTime(),
        place.getStoredCount(),
        place.getLatitude(),
        place.getLongitude(),
        place.getCommentCount(),
        place.getCity().getId(),
        imageUrl
    );
  }
}