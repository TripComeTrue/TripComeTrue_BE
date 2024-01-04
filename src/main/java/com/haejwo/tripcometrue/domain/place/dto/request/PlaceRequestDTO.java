package com.haejwo.tripcometrue.domain.place.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import java.time.LocalTime;
import lombok.Builder;

public record PlaceRequestDTO(
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
    public PlaceRequestDTO(
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

    public Place toEntity() {
        return Place.builder()
            .name(this.name)
            .address(this.address)
            .description(this.description)
            .weekdayOpenTime(this.weekdayOpenTime)
            .weekdayCloseTime(this.weekdayCloseTime)
            .weekendOpenTime(this.weekendOpenTime)
            .weekendCloseTime(this.weekendCloseTime)
            .storedCount(this.storedCount)
            .cityId(this.cityId)
            .build();
    }

}
