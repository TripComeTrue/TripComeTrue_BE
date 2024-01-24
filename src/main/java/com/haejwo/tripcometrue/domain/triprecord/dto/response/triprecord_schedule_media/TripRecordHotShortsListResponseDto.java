package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import lombok.Builder;

public record TripRecordHotShortsListResponseDto(

    Long tripRecordId,
    String tripRecordTitle,
    Integer tripRecordStoreCount,
    Long videoId,
    String thumbnailUrl,
    TripRecordMemberResponseDto member

) {

    @Builder
    public TripRecordHotShortsListResponseDto(Long tripRecordId, String tripRecordTitle,
        Integer tripRecordStoreCount, Long videoId, String thumbnailUrl,
        TripRecordMemberResponseDto member) {
        this.tripRecordId = tripRecordId;
        this.tripRecordTitle = tripRecordTitle;
        this.tripRecordStoreCount = tripRecordStoreCount;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.member = member;
    }
}
