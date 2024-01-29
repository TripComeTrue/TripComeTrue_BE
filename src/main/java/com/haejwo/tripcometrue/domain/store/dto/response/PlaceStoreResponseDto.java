package com.haejwo.tripcometrue.domain.store.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalTime;

public record PlaceStoreResponseDto(
    Long id,
    String name,
    Integer commentCount,
    Integer storedCount,
    String imageUrl
) {

  public static PlaceStoreResponseDto fromEntity(PlaceStore placeStore, String imageUrl) {
    Place place = placeStore.getPlace();
    return new PlaceStoreResponseDto(
        place.getId(),
        place.getName(),
        place.getCommentCount(),
        place.getStoredCount(),
        imageUrl
    );
  }
}