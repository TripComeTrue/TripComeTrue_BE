package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordRepository extends
    JpaRepository<TripRecord, Long>,
    TripRecordCustomRepository

{

  Optional<TripRecord> findById (Member member);
}
