package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule;

import com.haejwo.tripcometrue.domain.place.entity.Place;

public record SearchScheduleTripResponseDto(
    Long placeId,
    String address,
    String name
) {

    public static SearchScheduleTripResponseDto fromEntity(Place entity) {
        return new SearchScheduleTripResponseDto(
            entity.getId(),
            entity.getAddress(),
            entity.getName()
        );
    }
}
