package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewHistory;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_viewhistory.TripRecordViewHistoryResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewhistory.TripRecordViewHistoryRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripRecordViewHistoryService {

  private final TripRecordRepository tripRecordRepository;
  private final TripRecordViewHistoryRepository tripRecordViewHistoryRepository;

  @Transactional
  public void addViewHistory(PrincipalDetails principalDetails, Long tripRecordId) {
    TripRecord tripRecord = tripRecordRepository.findById(tripRecordId)
        .orElseThrow(TripRecordNotFoundException::new);

    Member member = principalDetails.getMember();
    Long memberId = member.getId();

    Optional<TripRecordViewHistory> existingHistory = tripRecordViewHistoryRepository
        .findByMemberIdAndTripRecordId(memberId, tripRecordId);

    TripRecordViewHistory history;
    if (existingHistory.isPresent()) {  //한번 봤던 기록인 경우
      history = existingHistory.get();
      history.updateUpdatedAt();
    } else {                            //처음 보는 기록인 경우
      history = TripRecordViewHistory.builder()
          .member(member)
          .tripRecord(tripRecord)
          .build();
    }

    tripRecordViewHistoryRepository.save(history);
  }

  @Transactional
  public Page<TripRecordViewHistoryResponseDto> getViewHistory(PrincipalDetails principalDetails, Pageable pageable) {

    return tripRecordViewHistoryRepository.findByMember(principalDetails.getMember(), pageable)
        .map(TripRecordViewHistoryResponseDto::fromEntity);
  }

}