package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule_media;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import lombok.Builder;

public record TripRecordScheduleVideoDetailDto(
    Long videoId,
    String videoUrl,
    Long tripRecordId,
    String tripRecordTitle,
    TripRecordMemberResponseDto member

) {

    @Builder
    public TripRecordScheduleVideoDetailDto(Long videoId, String videoUrl, Long tripRecordId,
        String tripRecordTitle, TripRecordMemberResponseDto member) {
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.tripRecordId = tripRecordId;
        this.tripRecordTitle = tripRecordTitle;
        this.member = member;
    }


    public static TripRecordScheduleVideoDetailDto fromEntity(TripRecordScheduleVideo entity) {

        return TripRecordScheduleVideoDetailDto.builder()
            .videoId(entity.getId())
            .videoUrl(entity.getVideoUrl())
            .tripRecordId(entity.getTripRecordSchedule().getTripRecord().getId())
            .tripRecordTitle(entity.getTripRecordSchedule().getTripRecord().getTitle())
            .member(TripRecordMemberResponseDto
                .fromEntity(entity.getTripRecordSchedule().getTripRecord().getMember()))
            .build();
    }
}
