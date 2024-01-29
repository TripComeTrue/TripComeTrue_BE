package com.haejwo.tripcometrue.domain.tripplan.dto.response;

import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import java.time.LocalDate;
import java.util.List;

public record TripPlanDetailsResponseDto(

    String countries,
    LocalDate tripStartDay,
    LocalDate tripEndDay,
    List<TripPlanScheduleResponseDto> tripPlanSchedules
) {

    public static TripPlanDetailsResponseDto fromEntity(TripPlan tripPlan,
        List<TripPlanScheduleResponseDto> tripPlanSchedules) {
        return new TripPlanDetailsResponseDto(
            tripPlan.getCountries(),
            tripPlan.getTripStartDay(),
            tripPlan.getTripEndDay(),
            tripPlanSchedules
        );
    }
}
