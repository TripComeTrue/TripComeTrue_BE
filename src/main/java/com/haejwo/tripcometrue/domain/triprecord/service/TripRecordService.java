package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewCount;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewcount.TripRecordViewCountRepository;
import com.haejwo.tripcometrue.domain.triprecordViewHistory.service.TripRecordViewHistoryService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripRecordService {

    private final TripRecordRepository tripRecordRepository;
    private final TripRecordViewCountRepository tripRecordViewCountRepository;
    private final TripRecordViewHistoryService tripRecordViewHistoryService;


    @Transactional
    public TripRecordDetailResponseDto findTripRecord(PrincipalDetails principalDetails, Long tripRecordId) {

        Long memberId = principalDetails.getMember().getId();
        TripRecord findTripRecord = findTripRecordById(tripRecordId);

        if(memberId != findTripRecord.getMember().getId()) { findTripRecord.incrementViewCount(); }
        incrementViewCount(findTripRecord);
        tripRecordViewHistoryService.addViewHistory(principalDetails, tripRecordId);

        TripRecordDetailResponseDto responseDto = TripRecordDetailResponseDto.fromEntity(findTripRecord);

        return responseDto;
    }

    @Transactional
    public List<TripRecordListResponseDto> findTripRecordList(
        Pageable pageable, TripRecordListRequestAttribute request
    ) {

        List<TripRecord> result = tripRecordRepository.finTripRecordWithFilter(pageable, request);

        List<TripRecordListResponseDto> responseDtos = result.stream()
                                    .map(TripRecordListResponseDto::fromEntity)
                                    .toList();

        return responseDtos;
    }


    @Transactional
    public void incrementViewCount(TripRecord tripRecord) {

        LocalDate today = LocalDate.now();

        TripRecordViewCount viewCountEntity = tripRecordViewCountRepository.findByTripRecordAndDate(tripRecord, today)
            .orElseGet(() -> createNewViewCount(tripRecord, today));

        viewCountEntity.incrementViewCount();
        tripRecordViewCountRepository.save(viewCountEntity);

    }

    private TripRecordViewCount createNewViewCount(TripRecord tripRecord, LocalDate today) {
        return TripRecordViewCount.builder()
            .date(today)
            .tripRecord(tripRecord)
            .viewCount(0)
            .build();
    }



    private TripRecord findTripRecordById(Long tripRecordId) {
        TripRecord findTripRecord = tripRecordRepository.findById(tripRecordId)
            .orElseThrow(TripRecordNotFoundException::new);
        return findTripRecord;
    }


    @Transactional(readOnly = true)
    public List<TripRecordListResponseDto> findMyTripRecordsList(
        PrincipalDetails principalDetails, Pageable pageable) {
        Long memberId = principalDetails.getMember().getId();
        List<TripRecord> tripRecords = tripRecordRepository.findByMemberId(memberId, pageable);

        return tripRecords.stream()
            .map(TripRecordListResponseDto::fromEntity)
            .collect(Collectors.toList());
    }

}
