package com.haejwo.tripcometrue.domain.triprecord.dto;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

public record TripRecordScheduleDto(
    Long id,
    Integer dayNumber,
    Integer ordering,
    String content,
    TripRecord tripRecord,
    Place place
) {

    @Builder
    public TripRecordScheduleDto(Long id, Integer dayNumber, Integer ordering, String content,
        TripRecord tripRecord, Place place) {
        this.id = id;
        this.dayNumber = dayNumber;
        this.ordering = ordering;
        this.content = content;
        this.tripRecord = tripRecord;
        this.place = place;
    }



}
