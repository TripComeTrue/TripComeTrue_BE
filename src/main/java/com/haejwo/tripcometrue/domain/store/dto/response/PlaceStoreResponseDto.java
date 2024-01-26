package com.haejwo.tripcometrue.domain.store.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;
import java.time.LocalTime;

public record PlaceStoreResponseDto(
    Long id,
    Integer storedCount,
    Long cityId
) {

  public static PlaceStoreResponseDto fromEntity(PlaceStore placeStore, String imageUrl) {
    Place place = placeStore.getPlace();
    return new PlaceStoreResponseDto(
        place.getId(),
        place.getStoredCount(),
        place.getCity().getId()
    );
  }
}