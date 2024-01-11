package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import jakarta.validation.constraints.NotNull;

public record CreateSchedulePlaceRequestDto(

    @NotNull(message = "address은 필수값입니다")
    String address,
    @NotNull(message = "name은 필수값입니다")
    String name,
    @NotNull(message = "latitude은 필수값입니다")
    Double latitude,
    @NotNull(message = "longitude은 필수값입니다")
    Double longitude
) {

    public Place toEntity() {
        return Place.builder()
            .name(this.name)
            .address(this.address)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .build();
    }
}
