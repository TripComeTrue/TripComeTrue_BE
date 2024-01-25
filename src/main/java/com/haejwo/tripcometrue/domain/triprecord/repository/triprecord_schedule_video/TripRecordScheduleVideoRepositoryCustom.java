package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule_video;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordScheduleVideoQueryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TripRecordScheduleVideoRepositoryCustom {

    Slice<TripRecordScheduleVideoQueryDto> findByCityId(Long cityId, Pageable pageable);

    List<TripRecordScheduleVideoQueryDto> findByCityIdOrderByCreatedAtDescLimitSize(Long cityId, Integer size);

    List<TripRecordScheduleVideoQueryDto> findNewestVideoList(int size);

    List<TripRecordScheduleVideoQueryDto> findNewestVideoListDomestic(int size);

    List<TripRecordScheduleVideoQueryDto> findNewestVideoListOverseas(int size);

    List<TripRecordScheduleVideoQueryDto> findVideoListInMemberIds(List<Long> memberIds);
}
