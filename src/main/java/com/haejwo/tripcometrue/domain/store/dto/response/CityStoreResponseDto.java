package com.haejwo.tripcometrue.domain.store.dto.response;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.global.enums.CurrencyUnit;
import com.haejwo.tripcometrue.domain.store.entity.CityStore;
import com.haejwo.tripcometrue.global.enums.Country;

public record CityStoreResponseDto(
    Long id,
    String name,
    Integer storeCount,
    String imageUrl
) {

  public static CityStoreResponseDto fromEntity(CityStore cityStore) {
    City city = cityStore.getCity();
    return new CityStoreResponseDto(
        city.getId(),
        city.getName(),
        city.getStoreCount(),
        city.getImageUrl()
    );
  }
}