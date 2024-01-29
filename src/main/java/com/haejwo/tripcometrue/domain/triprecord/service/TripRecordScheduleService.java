package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleImageListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule.TripRecordScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripRecordScheduleService {

    private final TripRecordScheduleRepository tripRecordScheduleRepository;

    public Page<TripRecordScheduleImageListResponseDto> findScheduleImages(
        Pageable pageable,
        TripRecordScheduleImageListRequestAttribute requestParam
    ) {

        Page<TripRecordScheduleImageListResponseDto> responseDtos = tripRecordScheduleRepository.findScheduleImagesWithFilter(pageable, requestParam);

        return responseDtos;

    }
}
