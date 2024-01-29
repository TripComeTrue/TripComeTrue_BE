package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.global.enums.Country;
import jakarta.validation.constraints.NotNull;

public record CreateSchedulePlaceRequestDto(

    @NotNull(message = "address은 필수값입니다")
    String address,
    @NotNull(message = "name은 필수값입니다")
    String name,
    @NotNull(message = "latitude은 필수값입니다")
    Double latitude,
    @NotNull(message = "longitude은 필수값입니다")
    Double longitude,
    @NotNull(message = "country은 필수값입니다")
    Country country,
    @NotNull(message = "cityname은 필수값입니다")
    String cityname
) {

    public Place toEntity(City city) {
        return Place.builder()
            .name(this.name)
            .address(this.address)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .city(city)
            .build();
    }
}
