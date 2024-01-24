package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import java.util.List;
import lombok.Builder;

public record MyTripRecordListResponseDto(
    Long tripRecordId,
    String title,
    String countries,
    Integer totalDays,
    Integer commentCount,
    Integer storeCount,
    String imageUrl,
    TripRecordMemberResponseDto member
) {

  @Builder
  public MyTripRecordListResponseDto(Long tripRecordId, String title, String countries, Integer totalDays,
      Integer commentCount, Integer storeCount, String imageUrl,
      TripRecordMemberResponseDto member) {
    this.tripRecordId = tripRecordId;
    this.title = title;
    this.countries = countries;
    this.totalDays = totalDays;
    this.commentCount = commentCount;
    this.storeCount = storeCount;
    this.imageUrl = imageUrl;
    this.member = member;
  }


  public static MyTripRecordListResponseDto fromEntity(TripRecord entity) {
    List<TripRecordImage> images = entity.getTripRecordImages();
    String imageUrl = images.isEmpty() ? null : images.get(0).getImageUrl();

    return MyTripRecordListResponseDto.builder()
        .tripRecordId(entity.getId())
        .title(entity.getTitle())
        .countries(entity.getCountries())
        .totalDays(entity.getTotalDays())
        .commentCount(entity.getCommentCount())
        .storeCount(entity.getStoreCount())
        .imageUrl(imageUrl)
        .build();
  }

}
