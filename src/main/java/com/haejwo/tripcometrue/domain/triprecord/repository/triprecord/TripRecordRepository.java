package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRecordRepository extends
    JpaRepository<TripRecord, Long>,
    TripRecordCustomRepository

{

  Page<TripRecord> findByMemberId(Long memberId, Pageable pageable);
  Optional<TripRecord> findById (Member member);
}
