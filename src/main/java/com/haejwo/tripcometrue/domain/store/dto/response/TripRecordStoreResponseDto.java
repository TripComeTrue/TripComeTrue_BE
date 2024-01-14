package com.haejwo.tripcometrue.domain.store.dto.response;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;

public record TripRecordStoreResponseDto(
    Long id,
    String title,
    String content,
    String expenseRangeType,
    String countries,
    LocalDate tripStartDay,
    LocalDate tripEndDay,
    Integer totalDays,
    Integer averageRating,
    Integer viewCount
) {

  public static TripRecordStoreResponseDto fromEntity(TripRecordStore tripRecordStore) {
    TripRecord tripRecord = tripRecordStore.getTripRecord();
    return new TripRecordStoreResponseDto(
        tripRecord.getId(),
        tripRecord.getTitle(),
        tripRecord.getContent(),
        tripRecord.getExpenseRangeType().name(), //todo
        tripRecord.getCountries(),
        tripRecord.getTripStartDay(),
        tripRecord.getTripEndDay(),
        tripRecord.getTotalDays(),
        tripRecord.getAverageRating(),
        tripRecord.getViewCount()
    );
  }
}