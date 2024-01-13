package com.haejwo.tripcometrue.domain.tripplan.repository;

import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlanSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlanScheduleRepository extends JpaRepository<TripPlanSchedule, Long> {

    void deleteAllByTripPlanId(Long tripPlanId);

    List<TripPlanSchedule> findAllByTripPlanId(Long tripPlanId);
}
