package com.haejwo.tripcometrue.domain.place.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import java.time.LocalTime;
import lombok.Builder;
public record PlaceResponseDTO(
    Long id,
    String name,
    String address,
    String description,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekdayOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekdayCloseTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekendOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekendCloseTime,
    Integer storedCount,
    Long cityId
) {

    @Builder
    public PlaceResponseDTO(
        Long id,
        String name,
        String address,
        String description,
        LocalTime weekdayOpenTime,
        LocalTime weekdayCloseTime,
        LocalTime weekendOpenTime,
        LocalTime weekendCloseTime,
        Integer storedCount,
        Long cityId
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.weekdayOpenTime = weekdayOpenTime;
        this.weekdayCloseTime = weekdayCloseTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
        this.storedCount = storedCount;
        this.cityId = cityId;
    }

    public static PlaceResponseDTO fromEntity(Place entity) {
        return PlaceResponseDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .address(entity.getAddress())
            .description(entity.getDescription())
            .weekdayOpenTime(entity.getWeekdayOpenTime())
            .weekdayCloseTime(entity.getWeekdayCloseTime())
            .weekendOpenTime(entity.getWeekendOpenTime())
            .weekendCloseTime(entity.getWeekendCloseTime())
            .storedCount(entity.getStoredCount())
            .cityId(entity.getCityId())
            .build();
    }

}
