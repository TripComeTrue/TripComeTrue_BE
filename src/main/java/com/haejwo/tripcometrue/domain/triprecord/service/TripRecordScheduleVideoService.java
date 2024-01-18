package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.NewestTripRecordScheduleVideoQueryDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.NewestTripRecordScheduleVideoResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video.TripRecordScheduleVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TripRecordScheduleVideoService {

    private final TripRecordScheduleVideoRepository tripRecordScheduleVideoRepository;

    private static final int HOME_CONTENT_SIZE = 5;

    public List<NewestTripRecordScheduleVideoResponseDto> getNewestVideos(String type) {
        List<NewestTripRecordScheduleVideoQueryDto> queryResults = new ArrayList<>();
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
}
