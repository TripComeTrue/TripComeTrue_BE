package com.haejwo.tripcometrue.domain.tripplan.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TripPlanListReponseDto(
    Long id,
    String countries,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate tripStartDay,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate tripEndDay,
    Integer totalDays,
    Integer averageRating,
    Integer viewCount,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    LocalDateTime createdAt,
    List<String> placesVisited
) {
  public static TripPlanListReponseDto fromEntity(TripPlan tripplan, List<String> placesVisited) {
    return new TripPlanListReponseDto(
        tripplan.getId(),
        tripplan.getCountries(),
        tripplan.getTripStartDay(),
        tripplan.getTripEndDay(),
        tripplan.getTotalDays(),
        tripplan.getAverageRating(),
        tripplan.getViewCount(),
        tripplan.getCreatedAt(),
        placesVisited
    );
  }
}