package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import lombok.Builder;

import java.util.Objects;

public record TripRecordListItemResponseDto(
    Long id,
    String title,
    Integer storedCount,
    String imageUrl
) {

    @Builder
    public TripRecordListItemResponseDto {
    }

    public static TripRecordListItemResponseDto fromEntity(TripRecord entity) {
        return TripRecordListItemResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .storedCount(entity.getStoreCount())
            .imageUrl(
                entity.getTripRecordImages()
                    .stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(TripRecordImage::getImageUrl)
                    .orElse(null)
            )
            .build();
    }
}
