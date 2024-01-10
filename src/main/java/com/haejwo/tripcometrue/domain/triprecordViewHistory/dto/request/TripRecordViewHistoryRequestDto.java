package com.haejwo.tripcometrue.domain.triprecordViewHistory.dto.request;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewHistory;

public record TripRecordViewHistoryRequestDto(
    Long memberId,
    Long tripRecordId
) {
  public TripRecordViewHistory toEntity(Member member, TripRecord tripRecord) {
    return TripRecordViewHistory.builder()
        .member(member)
        .tripRecord(tripRecord)
        .build();
  }
}