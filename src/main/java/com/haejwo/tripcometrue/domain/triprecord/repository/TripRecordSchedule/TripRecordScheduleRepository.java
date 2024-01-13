package com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordSchedule;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordScheduleRepository extends
    JpaRepository<TripRecordSchedule, Long>,
    TripRecordScheduleCustomRepository
{

}
