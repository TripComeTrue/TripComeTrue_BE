package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TripRecordScheduleRequestDto(

    @NotNull(message = "dayNumber은 필수값입니다")
    int dayNumber,
    @NotNull(message = "orderNumber은 필수값입니다")
    int orderNumber,
    @NotNull(message = "placeId은 필수값입니다")
    Long placeId,
    String content,
    List<String> tripRecordScheduleImages,
    List<String> tripRecordScheduleVideos,
    ExternalLinkTagType tagType,
    String tagUrl
) {

    public TripRecordSchedule toEntity(TripRecord tripRecord, Member member) {
        return TripRecordSchedule.builder()
            .dayNumber(this.dayNumber)
            .ordering(this.orderNumber)
            .content(this.content)
            .placeId(this.placeId)
            .tripRecord(tripRecord)
            .tagType(this.tagType)
            .tagUrl(this.tagUrl)
            .member(member)
            .build();
    }
}
