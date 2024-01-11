package com.haejwo.tripcometrue.domain.store.dto.request;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.store.entity.PlaceStore;


public record PlaceStoreRequestDto(Long placeId) {

  public PlaceStore toEntity(Member member, Place place) {
    return PlaceStore.builder()
        .member(member)
        .place(place)
        .build();
  }
}