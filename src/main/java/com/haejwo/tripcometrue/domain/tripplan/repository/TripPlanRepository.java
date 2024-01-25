package com.haejwo.tripcometrue.domain.tripplan.repository;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {

  Page<TripPlan> findByMemberId(Long memberId, Pageable pageable);

  @Query("select tp from TripPlan tp where tp.member = :member and tp.referencedBy = :tripRecordId order by tp.tripEndDay asc limit 1")
  Optional<TripPlan> findByMemberIdAndTripRecordId(@Param("member") Member loginMember, @Param("tripRecordId") Long tripRecordId);
}
