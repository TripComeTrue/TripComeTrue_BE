package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleImageListResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TripRecordScheduleCustomRepository {

    List<TripRecordScheduleImageListResponseDto> findScheduleImagesWithFilter(Pageable pageable, TripRecordScheduleImageListRequestAttribute request);

}
