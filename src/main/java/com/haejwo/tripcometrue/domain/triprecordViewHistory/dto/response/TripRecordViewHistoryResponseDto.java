package com.haejwo.tripcometrue.domain.triprecordViewHistory.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewHistory;
import java.time.LocalDateTime;

public record TripRecordViewHistoryResponseDto(
    Long id,
    Long memberId,
    Long tripRecordId,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
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