package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_tag;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordTagRepository extends JpaRepository<TripRecordTag, Long> {

    void deleteAllByTripRecordId(Long TripRecordId);
}
