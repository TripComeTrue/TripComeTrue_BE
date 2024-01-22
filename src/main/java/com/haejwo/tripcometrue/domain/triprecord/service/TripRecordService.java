package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordSearchParamAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.*;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordHotShortsListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoDetailDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewCount;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordScheduleVideoNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video.TripRecordScheduleVideoRepository;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewcount.TripRecordViewCountRepository;
import com.haejwo.tripcometrue.domain.triprecordViewHistory.service.TripRecordViewHistoryService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final TripRecordScheduleVideoRepository tripRecordScheduleVideoRepository;

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

        if(principalDetails != null) {
            tripRecordViewHistoryService.addViewHistory(principalDetails, tripRecordId);
        }

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
    public SliceResponseDto<TripRecordListItemResponseDto> findTripRecordList(
        TripRecordSearchParamAttribute searchParamAttribute,
        Pageable pageable
    ) {
        return SliceResponseDto.of(
            tripRecordRepository.findTripRecordListByFilter(searchParamAttribute, pageable)
                .map(tripRecord -> TripRecordListItemResponseDto.fromEntity(tripRecord, null, tripRecord.getMember()))
        );
    }

    @Transactional(readOnly = true)
    public SliceResponseDto<TripRecordListItemResponseDto> listTripRecordsByHashTag(
        String hashTag, Pageable pageable
    ) {
        return SliceResponseDto.of(
            tripRecordRepository.findTripRecordsByHashTag(hashTag, pageable)
                .map(tripRecord -> TripRecordListItemResponseDto.fromEntity(tripRecord, null, tripRecord.getMember()))
        );
    }

    @Transactional(readOnly = true)
    public List<TripRecordListItemResponseDto> findTopTripRecordList(String type) {

        List<TripRecord> tripRecords;
        if (type.equalsIgnoreCase("domestic")) {
            tripRecords = tripRecordRepository.findTopTripRecordListDomestic(HOME_CONTENT_SIZE);
        } else {
            tripRecords = tripRecordRepository.findTopTripRecordListOverseas(HOME_CONTENT_SIZE);
        }

        return tripRecords
            .stream()
            .map(tripRecord ->
                TripRecordListItemResponseDto.fromEntity(
                    tripRecord,
                    tripRecord.getTripRecordSchedules()
                        .stream()
                        .map(tripRecordSchedule ->
                            tripRecordSchedule.getPlace().getCity().getName()
                        ).collect(Collectors.toSet()),
                    tripRecord.getMember()
                )
            )
            .toList();
    }

    @Transactional(readOnly = true)
    public List<TripRecordListItemResponseDto> findTripRecordsWihMemberInMemberIds(List<Long> memberIds) {

        return tripRecordRepository
            .findTripRecordListWithMemberInMemberIds(memberIds)
            .stream()
            .map(tripRecord -> TripRecordListItemResponseDto.fromEntity(tripRecord, null, tripRecord.getMember()))
            .toList();
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
    public Page<MyTripRecordListResponseDto> getMyTripRecordsList(
        PrincipalDetails principalDetails, Pageable pageable) {
        Long memberId = principalDetails.getMember().getId();
        Page<TripRecord> tripRecords = tripRecordRepository.findByMemberId(memberId, pageable);

        return tripRecords.map(MyTripRecordListResponseDto::fromEntity);
    }

    public List<TripRecordHotShortsListResponseDto> findTripRecordHotShortsList(Pageable pageable) {

        List<TripRecordHotShortsListResponseDto> responseDtos = tripRecordRepository.findTripRecordHotShortsList(pageable);

        return responseDtos;
    }

    public TripRecordScheduleVideoDetailDto findTripRecordShortsDetail(Long videoId) {

        TripRecordScheduleVideo video = tripRecordScheduleVideoRepository.findById(videoId)
            .orElseThrow(TripRecordScheduleVideoNotFoundException::new);

        TripRecordScheduleVideoDetailDto responseDto = TripRecordScheduleVideoDetailDto.fromEntity(video);

        return responseDto;
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
