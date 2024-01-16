package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import lombok.Builder;

public record TripRecordListResponseDto(
    Long id,
    String title,
    String countries,
    Integer totalDays,
    Integer commentNumber,
    Integer storeNumber,
    TripRecordMemberResponseDto member
) {

    @Builder
    public TripRecordListResponseDto(Long id, String title, String countries, Integer totalDays,
        Integer commentNumber, Integer storeNumber, TripRecordMemberResponseDto member) {
        this.id = id;
        this.title = title;
        this.countries = countries;
        this.totalDays = totalDays;
        this.commentNumber = commentNumber;
        this.storeNumber = storeNumber;
        this.member = member;
    }

    public static TripRecordListResponseDto fromEntity(TripRecord entity) {
        return TripRecordListResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .countries(entity.getCountries())
            .totalDays(entity.getTotalDays())
            .build();
    }

}
