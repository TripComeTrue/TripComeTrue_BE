package com.haejwo.tripcometrue.domain.tripplan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record TripPlanRequestDto(

    @NotNull(message = "countries은 필수값입니다")
    String countries,
    @NotNull(message = "tripStartDay은 필수값입니다")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate tripStartDay,
    @NotNull(message = "tripEndDay은 필수값입니다")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate tripEndDay,
    Long referencedBy,
    List<TripPlanScheduleRequestDto> tripPlanSchedules
) {

    public TripPlan toEntity(Member member) {
        return TripPlan.builder()
            .countries(this.countries)
            .tripStartDay(this.tripStartDay)
            .tripEndDay(this.tripEndDay)
            .member(member)
            .referencedBy(this.referencedBy)
            .build();
    }
}
