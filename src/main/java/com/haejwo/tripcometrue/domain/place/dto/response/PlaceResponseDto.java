package com.haejwo.tripcometrue.domain.place.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import java.time.LocalTime;
import lombok.Builder;
public record PlaceResponseDto(
    Long id,
    String name,
    String address,
    String description,
    String phoneNumber,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekdayOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekdayCloseTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekendOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekendCloseTime,
    Double latitude,
    Double longitude,
    Integer storedCount,
    Long cityId
) {

    @Builder
    public PlaceResponseDto(Long id, String name, String address, String description,
        String phoneNumber,
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm") LocalTime weekdayOpenTime,
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm") LocalTime weekdayCloseTime,
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm") LocalTime weekendOpenTime,
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm") LocalTime weekendCloseTime,
        Double latitude, Double longitude, Integer storedCount, Long cityId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.weekdayOpenTime = weekdayOpenTime;
        this.weekdayCloseTime = weekdayCloseTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storedCount = storedCount;
        this.cityId = cityId;
    }

    public static PlaceResponseDto fromEntity(Place entity) {
        return PlaceResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .address(entity.getAddress())
            .description(entity.getDescription())
            .phoneNumber(entity.getPhoneNumber())
            .weekdayOpenTime(entity.getWeekdayOpenTime())
            .weekdayCloseTime(entity.getWeekdayCloseTime())
            .weekendOpenTime(entity.getWeekendOpenTime())
            .weekendCloseTime(entity.getWeekendCloseTime())
            .latitude(entity.getLatitude())
            .longitude(entity.getLongitude())
            .storedCount(entity.getStoredCount())
            .cityId(entity.getCity().getId())
            .build();
    }

}
