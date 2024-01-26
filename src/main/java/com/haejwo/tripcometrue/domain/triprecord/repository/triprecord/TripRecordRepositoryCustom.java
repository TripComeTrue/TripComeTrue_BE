package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.ModelAttribute.TripRecordSearchParamAttribute;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordHotShortsListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TripRecordRepositoryCustom {

    List<TripRecordListResponseDto> finTripRecordWithFilter(Pageable pageable, TripRecordListRequestAttribute request);

    Slice<TripRecord> findTripRecordsByFilter(TripRecordSearchParamAttribute requestParamAttribute, Pageable pageable);

    Slice<TripRecord> findTripRecordsByHashtag(String hashTag, Pageable pageable);

    Slice<TripRecord> findTripRecordsByExpenseRangeType(ExpenseRangeType expenseRangeType, Pageable pageable);

    List<TripRecord> findTopTripRecordsDomestic(int size);

    List<TripRecord> findTopTripRecordsOverseas(int size);

    List<TripRecordHotShortsListResponseDto> findTripRecordHotShortsList(Pageable pageable);

    List<TripRecord> findTripRecordsWithMemberInMemberIds(List<Long> memberIds);
}
