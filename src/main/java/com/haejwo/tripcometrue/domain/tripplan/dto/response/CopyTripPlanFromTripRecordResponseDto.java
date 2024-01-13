package com.haejwo.tripcometrue.domain.tripplan.dto.response;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;
import java.util.List;

public record CopyTripPlanFromTripRecordResponseDto(

    String countries,
    LocalDate tripStartDay,
    LocalDate tripEndDay,
    List<TripPlanScheduleResponseDto> tripSchedules
) {

    public static CopyTripPlanFromTripRecordResponseDto fromEntity(TripRecord tripRecord,
        List<TripPlanScheduleResponseDto> tripSchedules) {
        return new CopyTripPlanFromTripRecordResponseDto(
            tripRecord.getCountries(),
            tripRecord.getTripStartDay(),
            tripRecord.getTripEndDay(),
            tripSchedules
        );
    }
}
