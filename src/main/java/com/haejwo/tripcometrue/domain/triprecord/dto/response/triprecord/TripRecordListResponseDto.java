package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import lombok.Builder;

public record TripRecordListResponseDto(
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
    public TripRecordListResponseDto(Long tripRecordId, String title, String countries, Integer totalDays,
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


    public static TripRecordListResponseDto fromEntity(TripRecord entity) {
        return TripRecordListResponseDto.builder()
            .tripRecordId(entity.getId())
            .title(entity.getTitle())
            .countries(entity.getCountries())
            .totalDays(entity.getTotalDays())
            .build();
    }

}
