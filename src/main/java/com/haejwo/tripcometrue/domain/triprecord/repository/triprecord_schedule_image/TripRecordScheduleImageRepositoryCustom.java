package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_image;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordScheduleImageWithPlaceIdQueryDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TripRecordScheduleImageRepositoryCustom {

    Slice<TripRecordScheduleImage> findByCityId(Long cityId, Pageable pageable);

    List<TripRecordScheduleImage> findByCityIdOrderByCreatedAtDescLimitSize(Long cityId, Integer size);

    List<TripRecordScheduleImageWithPlaceIdQueryDto> findInPlaceIdsOrderByCreatedAtDesc(List<Long> placeIds);

}
