package com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordSchedule;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.ModelAttribute.TripRecordScheduleImageListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.schedule_media.TripRecordImageListResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TripRecordScheduleCustomRepository {

    List<TripRecordImageListResponseDto> findScheduleImagesWithFilter(Pageable pageable, TripRecordScheduleImageListRequestAttribute request);

}
