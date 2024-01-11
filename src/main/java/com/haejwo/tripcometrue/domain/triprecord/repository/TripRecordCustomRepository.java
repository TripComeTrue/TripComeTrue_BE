package com.haejwo.tripcometrue.domain.triprecord.repository;

import com.haejwo.tripcometrue.domain.triprecord.dto.TripRecordListRequestAttribute;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TripRecordCustomRepository {

    List<TripRecord> finTripRecordWithFilter(Pageable pageable, TripRecordListRequestAttribute request);

}
