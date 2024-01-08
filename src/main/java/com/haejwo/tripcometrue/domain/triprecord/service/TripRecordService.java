package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripRecordService {

    private final TripRecordRepository tripRecordRepository;

    @Transactional
    public TripRecordResponseDto addTripRecord(TripRecordRequestDto requestDto) {

        TripRecord requestTripRecord = requestDto.toEntity();
        TripRecord savedTripRecord = tripRecordRepository.save(requestTripRecord);
        TripRecordResponseDto responseDto = TripRecordResponseDto.fromEntity(savedTripRecord);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public TripRecordResponseDto findTripRecord(Long tripRecordId) {
        
        TripRecord findTripRecord = findTripRecordById(tripRecordId);
        TripRecordResponseDto responseDto = TripRecordResponseDto.fromEntity(findTripRecord);

        return responseDto;
    }

    @Transactional
    public TripRecordResponseDto modifyTripRecord(Long tripRecordId, TripRecordRequestDto requestDto) {

        TripRecord findTripRecord =  findTripRecordById(tripRecordId);
        findTripRecord.update(requestDto);
        TripRecordResponseDto responseDto = TripRecordResponseDto.fromEntity(findTripRecord);

        return responseDto;
    }

    public void removeTripRecord(Long tripRecordId) {
        TripRecord findTripRecord = findTripRecordById(tripRecordId);
        tripRecordRepository.delete(findTripRecord);
    }

    private TripRecord findTripRecordById(Long tripRecordId) {
        TripRecord findTripRecord = tripRecordRepository.findById(tripRecordId)
            .orElseThrow(); // TODO: 여행후기 조회 예외 추가
        return findTripRecord;
    }


}
