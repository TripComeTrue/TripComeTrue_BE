package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordSearchParamAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordHotShortsListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TripRecordCustomRepository {

    List<TripRecordListResponseDto> finTripRecordWithFilter(Pageable pageable, TripRecordListRequestAttribute request);

    Slice<TripRecord> findTripRecordListByFilter(TripRecordSearchParamAttribute requestParamAttribute, Pageable pageable);

    Slice<TripRecord> findTripRecordsByHashTag(String hashTag, Pageable pageable);

    List<TripRecord> findTopTripRecordListDomestic(int size);

    List<TripRecord> findTopTripRecordListOverseas(int size);

    List<TripRecordHotShortsListResponseDto> findTripRecordHotShortsList(Pageable pageable);

    List<TripRecord> findTripRecordListWithMemberInMemberIds(List<Long> memberIds);
}
