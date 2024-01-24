package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.NewestTripRecordScheduleVideoQueryDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.NewestTripRecordScheduleVideoResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoListItemResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video.TripRecordScheduleVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TripRecordScheduleVideoService {

    private final TripRecordScheduleVideoRepository tripRecordScheduleVideoRepository;

    private static final int HOME_CONTENT_SIZE = 5;

    @Transactional(readOnly = true)
    public List<NewestTripRecordScheduleVideoResponseDto> getNewestVideos(String type) {

        List<NewestTripRecordScheduleVideoQueryDto> queryResults;

        if (type.equalsIgnoreCase("all")) {
            queryResults = tripRecordScheduleVideoRepository.findNewestVideoList(HOME_CONTENT_SIZE);
        } else if (type.equalsIgnoreCase("domestic")) {
            queryResults = tripRecordScheduleVideoRepository.findNewestVideoListDomestic(HOME_CONTENT_SIZE);
        } else {
            queryResults = tripRecordScheduleVideoRepository.findNewestVideoListOverseas(HOME_CONTENT_SIZE);
        }

        return queryResults.stream()
            .map(NewestTripRecordScheduleVideoResponseDto::fromQueryDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<TripRecordScheduleVideoListItemResponseDto> getVideosInMemberIds(List<Long> memberIds) {

        return tripRecordScheduleVideoRepository
            .findVideoListInMemberIds(memberIds)
            .stream()
            .map(TripRecordScheduleVideoListItemResponseDto::fromQueryDto)
            .toList();
    }
}
