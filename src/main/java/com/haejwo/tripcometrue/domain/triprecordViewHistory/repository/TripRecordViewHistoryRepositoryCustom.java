package com.haejwo.tripcometrue.domain.triprecordViewHistory.repository;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordViewHistoryGroupByQueryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRecordViewHistoryRepositoryCustom {

    List<TripRecordViewHistoryGroupByQueryDto> findTopListMembers(LocalDateTime start, LocalDateTime end, int size);
}
