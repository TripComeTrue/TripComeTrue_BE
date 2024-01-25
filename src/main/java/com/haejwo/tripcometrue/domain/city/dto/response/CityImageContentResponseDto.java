package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import lombok.Builder;

public record CityImageContentResponseDto(
    Long tripRecordId,
    String imageUrl,
    Integer tripRecordStoreCount
) {
    @Builder
    public CityImageContentResponseDto {
    }

    public static CityImageContentResponseDto fromEntity(TripRecordSchedule entity, TripRecordScheduleImage image) {
        return CityImageContentResponseDto.builder()
            .tripRecordId(entity.getTripRecord().getId())
            .imageUrl(image.getImageUrl())
            .tripRecordStoreCount(entity.getTripRecord().getStoreCount())
            .build();
    }

    public static CityImageContentResponseDto fromEntity(TripRecordScheduleImage entity) {
        TripRecord tripRecord = entity.getTripRecordSchedule().getTripRecord();
        return CityImageContentResponseDto.builder()
            .tripRecordId(tripRecord.getId())
            .imageUrl(entity.getImageUrl())
            .tripRecordStoreCount(tripRecord.getStoreCount())
            .build();
    }
}
