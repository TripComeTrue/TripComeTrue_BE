package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordScheduleVideoQueryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TripRecordScheduleVideoRepositoryCustom {

    Slice<TripRecordScheduleVideoQueryDto> findByCityId(Long cityId, Pageable pageable);

    List<TripRecordScheduleVideoQueryDto> findByCityIdOrderByCreatedAtDescLimitSize(Long cityId, Integer size);

    List<TripRecordScheduleVideoQueryDto> findNewestVideos(int size);

    List<TripRecordScheduleVideoQueryDto> findNewestVideosDomestic(int size);

    List<TripRecordScheduleVideoQueryDto> findNewestVideosOverseas(int size);

    List<TripRecordScheduleVideoQueryDto> findInMemberIds(List<Long> memberIds);
}
