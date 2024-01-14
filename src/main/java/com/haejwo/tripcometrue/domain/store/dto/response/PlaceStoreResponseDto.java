package com.haejwo.tripcometrue.domain.store.dto.response;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;
import java.time.LocalTime;

public record PlaceStoreResponseDto(
    Long id,
    String name,
    String address,
    String description,
    LocalTime weekdayOpenTime,
    LocalTime weekdayCloseTime,
    LocalTime weekendOpenTime,
    LocalTime weekendCloseTime,
    Integer storedCount,
    Double latitude,
    Double longtitude,
    Long cityId
) {

  public static PlaceStoreResponseDto fromEntity(PlaceStore placeStore) {
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
        place.getCity().getId()
    );
  }
}