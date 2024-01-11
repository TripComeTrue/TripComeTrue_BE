package com.haejwo.tripcometrue.domain.tripplan.sevice;

import com.haejwo.tripcometrue.domain.tripplan.dto.request.TripPlanRequestDto;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import com.haejwo.tripcometrue.domain.tripplan.repository.TripPlanRepository;
import com.haejwo.tripcometrue.domain.tripplan.repository.TripPlanScheduleRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private final TripPlanRepository tripPlanRepository;
    private final TripPlanScheduleRepository tripPlanScheduleRepository;

    @Transactional
    public void addTripPlan(PrincipalDetails principalDetails, TripPlanRequestDto requestDto) {
        TripPlan requestTripPlan = requestDto.toEntity(principalDetails.getMember());
        tripPlanRepository.save(requestTripPlan);

        requestDto.tripPlanSchedules().stream()
            .map(tripPlanScheduleRequestDto ->
                tripPlanScheduleRequestDto.toEntity(requestTripPlan, principalDetails.getMember()))
            .forEach(tripPlanScheduleRepository::save);
    }
}
