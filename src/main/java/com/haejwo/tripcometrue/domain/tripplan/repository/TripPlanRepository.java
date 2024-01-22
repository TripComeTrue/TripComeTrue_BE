package com.haejwo.tripcometrue.domain.tripplan.repository;

import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {

  Page<TripPlan> findByMemberId(Long memberId, Pageable pageable);
}
