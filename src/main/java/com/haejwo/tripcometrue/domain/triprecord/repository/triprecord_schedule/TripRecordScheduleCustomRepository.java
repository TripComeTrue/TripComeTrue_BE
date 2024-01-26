package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleImageListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripRecordScheduleCustomRepository {

    Page<TripRecordScheduleImageListResponseDto> findScheduleImagesWithFilter(Pageable pageable, TripRecordScheduleImageListRequestAttribute request);

}
