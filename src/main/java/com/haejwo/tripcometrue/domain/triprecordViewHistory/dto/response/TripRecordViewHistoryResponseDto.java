package com.haejwo.tripcometrue.domain.triprecordViewHistory.dto.response;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewHistory;
import java.time.LocalDateTime;

public record TripRecordViewHistoryResponseDto(
    Long id,
    Long memberId,
    Long tripRecordId,
    LocalDateTime createdAt
) {
  public static TripRecordViewHistoryResponseDto fromEntity(
      TripRecordViewHistory tripRecordViewHistory) {
    return new TripRecordViewHistoryResponseDto(
        tripRecordViewHistory.getId(),
        tripRecordViewHistory.getMember().getId(),
        tripRecordViewHistory.getTripRecord().getId(),
        tripRecordViewHistory.getCreatedAt()
    );
  }
}