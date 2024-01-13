package com.haejwo.tripcometrue.domain.triprecord.service;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.schedule_media.TripRecordImageListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordSchedule.TripRecordScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripRecordScheduleService {

    private final TripRecordScheduleRepository tripRecordScheduleRepository;

    public List<TripRecordImageListResponseDto> findScheduleImages(
        Pageable pageable,
        TripRecordScheduleImageListRequestAttribute requestParam
    ) {

        List<TripRecordImageListResponseDto> responseDtos = tripRecordScheduleRepository.findScheduleImagesWithFilter(pageable, requestParam);

        return responseDtos;

    }
}
