package com.haejwo.tripcometrue.domain.place.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import lombok.Builder;

import java.time.LocalTime;

public record PlaceListItemResponseDto(
    Long placeId,
    String placeName,
    String address,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime weekdayOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime weekdayCloseTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime weekendOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime weekendCloseTime,
    String telNumber,
    String cityName,
    String imageUrl
) {

    @Builder
    public PlaceListItemResponseDto {
    }

    public static PlaceListItemResponseDto fromEntity(Place entity, String imageUrl) {
        return PlaceListItemResponseDto.builder()
            .placeId(entity.getId())
            .placeName(entity.getName())
            .address(entity.getAddress())
            .weekdayOpenTime(entity.getWeekdayOpenTime())
            .weekdayCloseTime(entity.getWeekdayCloseTime())
            .weekendOpenTime(entity.getWeekendOpenTime())
            .weekendCloseTime(entity.getWeekendCloseTime())
            .cityName(entity.getCity().getName())
            .imageUrl(imageUrl)
            .build(); // TODO: telNumber mapping
    }
}
