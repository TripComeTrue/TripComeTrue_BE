package com.haejwo.tripcometrue.domain.tripplan.dto.request;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlan;
import com.haejwo.tripcometrue.domain.tripplan.entity.TripPlanSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import jakarta.validation.constraints.NotNull;

public record TripPlanScheduleRequestDto(

    @NotNull(message = "dayNumber은 필수값입니다")
    int dayNumber,
    @NotNull(message = "orderNumber은 필수값입니다")
    int orderNumber,
    @NotNull(message = "placeId은 필수값입니다")
    Long placeId,
    String content,
    ExternalLinkTagType tagType,
    String tagUrl
) {

    public TripPlanSchedule toEntity(TripPlan tripPlan, Member member) {
        return TripPlanSchedule.builder()
            .dayNumber(this.dayNumber)
            .ordering(this.orderNumber)
            .content(this.content)
            .placeId(this.placeId)
            .tripPlan(tripPlan)
            .tagType(this.tagType)
            .tagUrl(this.tagUrl)
            .member(member)
            .build();
    }
}
