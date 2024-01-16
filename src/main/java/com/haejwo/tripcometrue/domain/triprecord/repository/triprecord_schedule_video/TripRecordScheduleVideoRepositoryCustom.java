package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TripRecordScheduleVideoRepositoryCustom {

    Slice<TripRecordScheduleVideo> findByCityId(Long cityId, Pageable pageable);

    List<TripRecordScheduleVideo> findByCityIdOrderByCreatedAtDescLimitSize(Long cityId, Integer size);
}
