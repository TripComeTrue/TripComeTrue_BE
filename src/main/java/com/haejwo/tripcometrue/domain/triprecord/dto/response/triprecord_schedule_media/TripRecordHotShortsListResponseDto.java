package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import lombok.Builder;

public record TripRecordHotShortsListResponseDto(

    Long tripRecordId,
    Long videoId,
    String thumbnailUrl,
    TripRecordMemberResponseDto member

) {

    @Builder
    public TripRecordHotShortsListResponseDto(Long tripRecordId, Long videoId, String thumbnailUrl,
        TripRecordMemberResponseDto member) {
        this.tripRecordId = tripRecordId;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.member = member;
    }
}
