package com.haejwo.tripcometrue.domain.member.dto.response;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord.TripRecordListItemResponseDto;
import lombok.Builder;

import java.util.List;

public record MemberInfoWithTripRecordsResponseDto(
    MemberSimpleResponseDto memberInfo,
    List<TripRecordListItemResponseDto> tripRecords
) {

    @Builder
    public MemberInfoWithTripRecordsResponseDto {
    }
}
