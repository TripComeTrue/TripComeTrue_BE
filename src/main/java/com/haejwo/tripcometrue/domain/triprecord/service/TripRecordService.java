package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.TripRecordResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripRecordService {

    private final TripRecordRepository tripRecordRepository;

    @Transactional
    public TripRecordResponseDto addTripRecord(PrincipalDetails principalDetails, TripRecordRequestDto requestDto) {

        TripRecord requestTripRecord = requestDto.toEntity(principalDetails.getMember());
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
    public TripRecordResponseDto modifyTripRecord(PrincipalDetails principalDetails, Long tripRecordId, TripRecordRequestDto requestDto) {

        TripRecord findTripRecord =  findTripRecordById(tripRecordId);

        if(principalDetails.getMember().getId() != findTripRecord.getMember().getId()) {
            throw new UserInvalidAccessException();
        }
        
        findTripRecord.update(requestDto);
        TripRecordResponseDto responseDto = TripRecordResponseDto.fromEntity(findTripRecord);

        return responseDto;
    }

    public void removeTripRecord(PrincipalDetails principalDetails, Long tripRecordId) {

        TripRecord findTripRecord = findTripRecordById(tripRecordId);

        if(principalDetails.getMember().getId() != findTripRecord.getMember().getId()) {
            throw new UserInvalidAccessException();
        }

        tripRecordRepository.delete(findTripRecord);
    }

    private TripRecord findTripRecordById(Long tripRecordId) {
        TripRecord findTripRecord = tripRecordRepository.findById(tripRecordId)
            .orElseThrow(TripRecordNotFoundException::new);
        return findTripRecord;
    }

}
