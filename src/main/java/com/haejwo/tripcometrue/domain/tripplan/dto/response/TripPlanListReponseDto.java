package com.haejwo.tripcometrue.domain.tripplan.dto.response;

import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TripPlanListReponseDto(
    Long id,
    String countries,
    LocalDate tripStartDay,
    LocalDate tripEndDay,
    Integer totalDays,
    Integer averageRating,
    Integer viewCount,
    LocalDateTime createdAt
) {
  public static TripPlanListReponseDto fromEntity(TripPlan tripplan) {
    return new TripPlanListReponseDto(
        tripplan.getId(),
        tripplan.getCountries(),
        tripplan.getTripStartDay(),
        tripplan.getTripEndDay(),
        tripplan.getTotalDays(),
        tripplan.getAverageRating(),
        tripplan.getViewCount(),
        tripplan.getCreatedAt()
    );
  }
}