package com.haejwo.tripcometrue.domain.place.dto;

import java.time.LocalTime;
import lombok.Builder;

public record PlaceDto(
    Long id,
    String name,
    String address,
    String description,
    LocalTime weekdayOpenTime,
    LocalTime weekdayCloseTime,
    LocalTime weekendOpenTime,
    LocalTime weekendCloseTime,
    Integer storedCount) {

    @Builder
    public PlaceDto(
        Long id,
        String name,
        String address,
        String description,
        LocalTime weekdayOpenTime,
        LocalTime weekdayCloseTime,
        LocalTime weekendOpenTime,
        LocalTime weekendCloseTime,
        Integer storedCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.weekdayOpenTime = weekdayOpenTime;
        this.weekdayCloseTime = weekdayCloseTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
        this.storedCount = storedCount;
    }
}
