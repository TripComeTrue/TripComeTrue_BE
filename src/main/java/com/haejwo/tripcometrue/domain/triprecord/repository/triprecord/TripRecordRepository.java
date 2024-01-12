package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordRepository extends
    JpaRepository<TripRecord, Long>,
    TripRecordCustomRepository

{

}
