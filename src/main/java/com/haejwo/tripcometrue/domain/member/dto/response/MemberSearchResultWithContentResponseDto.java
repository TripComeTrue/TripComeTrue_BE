package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListItemResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media.TripRecordScheduleVideoListItemResponseDto;
import lombok.Builder;

import java.util.List;

public record MemberSearchResultWithContentResponseDto(
    List<MemberSimpleResponseDto> members,
    List<TripRecordScheduleVideoListItemResponseDto> videos,
    List<TripRecordListItemResponseDto> tripRecords
) {

    @Builder
    public MemberSearchResultWithContentResponseDto {
    }
}
