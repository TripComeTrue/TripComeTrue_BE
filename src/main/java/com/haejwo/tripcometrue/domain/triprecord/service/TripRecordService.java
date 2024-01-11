package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
import com.haejwo.tripcometrue.domain.triprecord.dto.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewCount;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordViewCountRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripRecordService {

    private final TripRecordRepository tripRecordRepository;
    private final TripRecordViewCountRepository tripRecordViewCountRepository;

    @Transactional
    public TripRecordResponseDto addTripRecord(PrincipalDetails principalDetails, TripRecordRequestDto requestDto) {

        TripRecord requestTripRecord = requestDto.toEntity(principalDetails.getMember());
        TripRecord savedTripRecord = tripRecordRepository.save(requestTripRecord);
        TripRecordResponseDto responseDto = TripRecordResponseDto.fromEntity(savedTripRecord);

        return responseDto;
    }

    @Transactional
    public TripRecordDetailResponseDto findTripRecord(PrincipalDetails principalDetails, Long tripRecordId) {

        Long memberId = principalDetails.getMember().getId();
        TripRecord findTripRecord = findTripRecordById(tripRecordId);

        if(memberId != findTripRecord.getMember().getId()) { findTripRecord.incrementViewCount(); }
        incrementViewCount(findTripRecord);

        TripRecordDetailResponseDto responseDto = TripRecordDetailResponseDto.fromEntity(findTripRecord);

        return responseDto;
    }

//    public List<TripRecordListResponseDto> findTripRecordList(String hashtag, String expenseRangeType, Long totalDays, Long placeId) {
//    }

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
    public TripRecordResponseDto modifyTripRecord(PrincipalDetails principalDetails, Long tripRecordId, TripRecordRequestDto requestDto) {

        TripRecord findTripRecord =  findTripRecordById(tripRecordId);

        if(principalDetails.getMember().getId() != findTripRecord.getMember().getId()) {
            throw new UserInvalidAccessException();
        }
        
        findTripRecord.update(requestDto);
        TripRecordResponseDto responseDto = TripRecordResponseDto.fromEntity(findTripRecord);

        return responseDto;
    }

    @Transactional
    public void removeTripRecord(PrincipalDetails principalDetails, Long tripRecordId) {

        TripRecord findTripRecord = findTripRecordById(tripRecordId);

        if(principalDetails.getMember().getId() != findTripRecord.getMember().getId()) {
            throw new UserInvalidAccessException();
        }

        tripRecordRepository.delete(findTripRecord);
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



}
