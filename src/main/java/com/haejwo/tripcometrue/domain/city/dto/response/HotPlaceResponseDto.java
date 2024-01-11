package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import lombok.Builder;

import java.util.Objects;

public record HotPlaceResponseDto(
    Long placeId,
    String placeName,
    Integer storedCount,
    String imageUrl
) {

    @Builder
    public HotPlaceResponseDto {
    }

    public static HotPlaceResponseDto fromEntity(Place entity, TripRecordScheduleImage image) {
        return HotPlaceResponseDto.builder()
            .placeId(entity.getId())
            .placeName(entity.getName())
            .storedCount(entity.getStoredCount())
            .imageUrl(Objects.isNull(image) ? null : image.getImageUrl())
            .build();
    }
}
