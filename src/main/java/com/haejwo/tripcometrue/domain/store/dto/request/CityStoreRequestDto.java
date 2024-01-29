package com.haejwo.tripcometrue.domain.store.dto.request;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.store.entity.CityStore;


public record CityStoreRequestDto(Long cityId) {

  public CityStore toEntity(Member member, City city) {
    return CityStore.builder()
        .member(member)
        .city(city)
        .build();
  }
}