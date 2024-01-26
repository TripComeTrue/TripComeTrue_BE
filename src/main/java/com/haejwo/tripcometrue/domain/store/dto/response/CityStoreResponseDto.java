package com.haejwo.tripcometrue.domain.store.dto.response;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.city.entity.CurrencyUnit;
import com.haejwo.tripcometrue.domain.store.entity.CityStore;
import com.haejwo.tripcometrue.global.enums.Country;

public record CityStoreResponseDto(
    Long id,
    Integer storeCount
) {

  public static CityStoreResponseDto fromEntity(CityStore cityStore) {
    City city = cityStore.getCity();
    return new CityStoreResponseDto(
        city.getId(),
        city.getStoreCount()
    );
  }
}