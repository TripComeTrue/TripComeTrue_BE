package com.haejwo.tripcometrue.domain.store.dto.request;
import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.store.entity.CityStore;
import lombok.Getter;

@Getter
public class CityStoreRequestDto {
  private Long cityId;

  public CityStore toEntity(Member member, City city) {
    return CityStore.builder()
        .member(member)
        .city(city)
        .build();
  }
}
