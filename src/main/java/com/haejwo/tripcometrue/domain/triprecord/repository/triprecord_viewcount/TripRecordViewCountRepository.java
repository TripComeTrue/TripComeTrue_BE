package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewcount;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordViewCount;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordViewCountRepository extends JpaRepository<TripRecordViewCount, Long> {

    Optional<TripRecordViewCount> findByTripRecordAndDate(TripRecord tripRecord, LocalDate date);

}
