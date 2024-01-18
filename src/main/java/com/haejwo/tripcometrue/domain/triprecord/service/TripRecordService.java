package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TopTripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewCount;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewcount.TripRecordViewCountRepository;
import com.haejwo.tripcometrue.domain.triprecordViewHistory.service.TripRecordViewHistoryService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripRecordService {

    private final TripRecordRepository tripRecordRepository;
    private final TripRecordViewCountRepository tripRecordViewCountRepository;
    private final TripRecordViewHistoryService tripRecordViewHistoryService;

    private static final int HOME_CONTENT_SIZE = 5;

    @Transactional
    public TripRecordDetailResponseDto findTripRecord(PrincipalDetails principalDetails, Long tripRecordId) {

        TripRecord findTripRecord = findTripRecordById(tripRecordId);

        Long memberId = null;

        if(principalDetails != null){
            memberId = principalDetails.getMember().getId();
        }

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

        List<TripRecordListResponseDto> result = tripRecordRepository.finTripRecordWithFilter(pageable, request);


        return result;
    }

    @Transactional(readOnly = true)
    public List<TopTripRecordResponseDto> findTopTripRecordList(String type) {
        if (type.equalsIgnoreCase("domestic")) {
            return tripRecordRepository
                .findTopTripRecordListDomestic(HOME_CONTENT_SIZE)
                .stream()
                .map(TopTripRecordResponseDto::fromEntity)
                .toList();
        } else {
            return tripRecordRepository
                .findTopTripRecordListOverseas(HOME_CONTENT_SIZE)
                .stream()
                .map(TopTripRecordResponseDto::fromEntity)
                .toList();
        }
    }

    @Transactional
    public void incrementViewCount(TripRecord tripRecord) {

        LocalDate today = LocalDate.now();

        TripRecordViewCount viewCountEntity = tripRecordViewCountRepository.findByTripRecordAndDate(tripRecord, today)
            .orElseGet(() -> createNewViewCount(tripRecord, today));

        viewCountEntity.incrementViewCount();
        tripRecordViewCountRepository.save(viewCountEntity);

    }

    @Transactional(readOnly = true)
    public List<TripRecordListResponseDto> getMyTripRecordsList(
        PrincipalDetails principalDetails, Pageable pageable) {
        Long memberId = principalDetails.getMember().getId();
        List<TripRecord> tripRecords = tripRecordRepository.findByMemberId(memberId, pageable);

        return tripRecords.stream()
            .map(TripRecordListResponseDto::fromEntity)
            .collect(Collectors.toList());
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

}
