package com.haejwo.tripcometrue.domain.city.dto.response;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import lombok.Builder;

public record CityVideoContentResponseDto(
    Long id,
    Long tripRecordId,
    String tripRecordTitle,
    String videoUrl,
    Integer storedCount
) {

    @Builder
    public CityVideoContentResponseDto {
    }

    public static CityVideoContentResponseDto fromEntity(TripRecordScheduleVideo entity) {
        TripRecord tripRecord = entity.getTripRecordSchedule().getTripRecord();

        return CityVideoContentResponseDto.builder()
            .id(entity.getId())
            .tripRecordId(tripRecord.getId())
            .tripRecordTitle(tripRecord.getTitle())
            .videoUrl(entity.getVideoUrl())
            .storedCount(tripRecord.getStoredCount())
            .build();
    }
}
