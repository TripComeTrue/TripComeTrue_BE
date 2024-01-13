package com.haejwo.tripcometrue.domain.tripplan.dto.response;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlanSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.global.enums.Country;

public record TripPlanScheduleResponseDto(

    Double latitude,
    Double longitude,
    Country country,
    String cityName,
    String placeName,
    Integer dayNumber,
    Integer orderNumber,
    Long placeId,
    String content,
    String tagType,
    String tagUrl

) {

    public static TripPlanScheduleResponseDto fromEntity(TripPlanSchedule tripPlanSchedule,
        Place place) {
        return new TripPlanScheduleResponseDto(
            place.getLatitude(),
            place.getLongitude(),
            place.getCity().getCountry(),
            place.getCity().getName(),
            place.getName(),
            tripPlanSchedule.getDayNumber(),
            tripPlanSchedule.getOrdering(),
            place.getId(),
            tripPlanSchedule.getContent(),
            tripPlanSchedule.getTagType().name(),
            tripPlanSchedule.getTagUrl()
        );
    }

    public static TripPlanScheduleResponseDto fromEntity(TripRecordSchedule tripRecordSchedule,
        Place place) {
        return new TripPlanScheduleResponseDto(
            place.getLatitude(),
            place.getLongitude(),
            place.getCity().getCountry(),
            place.getCity().getName(),
            place.getName(),
            tripRecordSchedule.getDayNumber(),
            tripRecordSchedule.getOrdering(),
            place.getId(),
            tripRecordSchedule.getContent(),
            tripRecordSchedule.getTagType().name(),
            tripRecordSchedule.getTagUrl()
        );
    }
}
