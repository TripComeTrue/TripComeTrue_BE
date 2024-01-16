package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import lombok.Builder;

public record CityImageContentResponseDto(
    Long id,
    Long tripRecordId,
    Integer storedCount,
    String imageUrl
) {
    @Builder
    public CityImageContentResponseDto {
    }

    public static CityImageContentResponseDto fromEntity(TripRecordSchedule entity, TripRecordScheduleImage image) {
        return CityImageContentResponseDto.builder()
            .id(image.getId())
            .tripRecordId(entity.getTripRecord().getId())
            .storedCount(entity.getTripRecord().getStoreCount())
            .imageUrl(image.getImageUrl())
            .build();
    }

    public static CityImageContentResponseDto fromEntity(TripRecordScheduleImage entity) {
        TripRecord tripRecord = entity.getTripRecordSchedule().getTripRecord();
        return CityImageContentResponseDto.builder()
            .id(entity.getId())
            .tripRecordId(tripRecord.getId())
            .storedCount(tripRecord.getStoreCount())
            .imageUrl(entity.getImageUrl())
            .build();
    }
}
