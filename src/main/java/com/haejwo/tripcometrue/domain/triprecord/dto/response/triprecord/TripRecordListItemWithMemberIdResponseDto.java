package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import lombok.Builder;

import java.util.Objects;

public record TripRecordListItemWithMemberIdResponseDto(
    Long id,
    String title,
    Integer storedCount,
    String imageUrl,
    Long memberId
) {

    @Builder
    public TripRecordListItemWithMemberIdResponseDto {
    }

    public static TripRecordListItemWithMemberIdResponseDto fromEntity(TripRecord entity) {
        return TripRecordListItemWithMemberIdResponseDto.builder()
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
            .memberId(entity.getMember().getId())
            .build();
    }
}
