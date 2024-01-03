package com.haejwo.tripcometrue.domain.place.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PlaceRequestDto(
    String name,
    String address,
    String description,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekdayOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekdayCloseTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekendOpenTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime weekendCloseTime,
//    LocalTime weekdayOpenTime,
//    LocalTime weekdayCloseTime,
//    LocalTime weekendOpenTime,
//    LocalTime weekendCloseTime,
    Integer storedCount,
    Long cityId
) {

    @Builder
    public PlaceRequestDto(
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

    public Place DtoToEntity() {
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
