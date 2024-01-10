package com.haejwo.tripcometrue.domain.store.dto.request;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import lombok.Getter;

@Getter
public class TripRecordStoreRequestDto {

  private Long tripRecordId;

  public TripRecordStore toEntity(Member member, TripRecord tripRecord) {
    return TripRecordStore.builder()
        .member(member)
        .tripRecord(tripRecord)
        .build();
  }

}
