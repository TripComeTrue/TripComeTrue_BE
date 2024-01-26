package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_schedule;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordScheduleRepository extends
    JpaRepository<TripRecordSchedule, Long>,
    TripRecordScheduleRepositoryCustom {


    List<TripRecordSchedule> findAllByTripRecordId(Long recordId);
    void deleteAllByTripRecordId(Long recordId);
}
