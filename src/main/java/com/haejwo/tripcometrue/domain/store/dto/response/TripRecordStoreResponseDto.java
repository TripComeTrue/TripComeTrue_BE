package com.haejwo.tripcometrue.domain.store.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;

public record TripRecordStoreResponseDto(
    Long id,
    Integer storeCount
) {

  public static TripRecordStoreResponseDto fromEntity(TripRecordStore tripRecordStore, String imageUrl) {
    TripRecord tripRecord = tripRecordStore.getTripRecord();
    return new TripRecordStoreResponseDto(
        tripRecord.getId(),
        tripRecord.getStoreCount()
    );
  }
}