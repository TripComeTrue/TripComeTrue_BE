package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule;

import com.haejwo.tripcometrue.domain.triprecord.dto.response.schedule_media.TripRecordScheduleImageResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.schedule_media.TripRecordScheduleVideoResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordScheduleVideo;
import java.util.List;
import lombok.Builder;

public record TripRecordScheduleDetailResponseDto(
    Long id,
    Integer dayNumber,
    Integer ordering,
    String content,
    Long placeId,
    Long tripRecordId,
    List<TripRecordScheduleImageResponseDto> images,
    List<TripRecordScheduleVideoResponseDto> videos
) {

    @Builder
    public TripRecordScheduleDetailResponseDto(Long id, Integer dayNumber, Integer ordering,
        String content, Long placeId, Long tripRecordId,
        List<TripRecordScheduleImageResponseDto> images,
        List<TripRecordScheduleVideoResponseDto> videos) {
        this.id = id;
        this.dayNumber = dayNumber;
        this.ordering = ordering;
        this.content = content;
        this.placeId = placeId;
        this.tripRecordId = tripRecordId;
        this.images = images;
        this.videos = videos;
    }

    public static TripRecordScheduleDetailResponseDto fromEntity(TripRecordSchedule entity) {

        List<TripRecordScheduleImage> images = entity.getTripRecordScheduleImages();
        List<TripRecordScheduleImageResponseDto> imageDtos = images.stream()
                                    .map(TripRecordScheduleImageResponseDto::fromEntity)
                                    .toList();

        List<TripRecordScheduleVideo> videos = entity.getTripRecordScheduleVideos();
        List<TripRecordScheduleVideoResponseDto> videoDtos = videos.stream()
                                    .map(TripRecordScheduleVideoResponseDto::fromEntity)
                                    .toList();

        return TripRecordScheduleDetailResponseDto.builder()
            .id(entity.getId())
            .dayNumber(entity.getDayNumber())
            .ordering(entity.getOrdering())
            .content(entity.getContent())
            .placeId(entity.getPlaceId())
            .tripRecordId(entity.getTripRecord() != null ? entity.getTripRecord().getId() : null)
            .images(imageDtos)
            .videos(videoDtos)
            .build();
    }


}
