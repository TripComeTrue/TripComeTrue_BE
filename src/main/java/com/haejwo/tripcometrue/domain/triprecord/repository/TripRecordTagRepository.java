package com.haejwo.tripcometrue.domain.triprecord.repository;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordTagRepository extends JpaRepository<TripRecordTag, Long> {

}
