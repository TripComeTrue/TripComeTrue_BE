package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.member.TripRecordMemberResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.record_media_tag.TripRecordImageResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.record_media_tag.TripRecordTagResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord_schedule.TripRecordScheduleDetailResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordTag;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;

public record TripRecordDetailResponseDto(
    Long id,
    String title,
    String content,
    ExpenseRangeType expenseRangeType,
    String countries,
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
    Integer totalDays,
    Integer average_rating,
    Integer viewCount,
    TripRecordMemberResponseDto member,
    List<TripRecordImageResponseDto> images,
    List<TripRecordTagResponseDto> tags,
    Map<Integer, List<TripRecordScheduleDetailResponseDto>> schedules

) {

    @Builder
    public TripRecordDetailResponseDto(Long id, String title, String content,
        ExpenseRangeType expenseRangeType, String countries,
        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
        Integer totalDays, Integer average_rating, Integer viewCount,
        TripRecordMemberResponseDto member, List<TripRecordImageResponseDto> images,
        List<TripRecordTagResponseDto> tags,
        Map<Integer, List<TripRecordScheduleDetailResponseDto>> schedules) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expenseRangeType = expenseRangeType;
        this.countries = countries;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.totalDays = totalDays;
        this.average_rating = average_rating;
        this.viewCount = viewCount;
        this.member = member;
        this.images = images;
        this.tags = tags;
        this.schedules = schedules;
    }


    public static TripRecordDetailResponseDto fromEntity(TripRecord entity) {

        TripRecordMemberResponseDto member = TripRecordMemberResponseDto.formEntity(entity.getMember());

        List<TripRecordImage> images = entity.getTripRecordImages();
        List<TripRecordImageResponseDto> imageDtos = images.stream()
                        .map(TripRecordImageResponseDto::fromEntity)
                        .toList();

        List<TripRecordTag> tags = entity.getTripRecordTags();
        List<TripRecordTagResponseDto> tagDtos = tags.stream()
                        .map(TripRecordTagResponseDto::fromEntity)
                        .toList();

        List<TripRecordSchedule> schedules = entity.getTripRecordSchedules();
        Map<Integer, List<TripRecordScheduleDetailResponseDto>> scheduleDtos = schedules.stream()
            .map(TripRecordScheduleDetailResponseDto::fromEntity)
            .sorted(Comparator.comparing(TripRecordScheduleDetailResponseDto::ordering))
            .collect(Collectors.groupingBy(TripRecordScheduleDetailResponseDto::dayNumber));

        return TripRecordDetailResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .expenseRangeType(entity.getExpenseRangeType())
            .countries(entity.getCountries())
            .tripStartDay(entity.getTripStartDay())
            .tripEndDay(entity.getTripEndDay())
            .totalDays(entity.getTotalDays())
            .viewCount(entity.getViewCount())
            .average_rating(entity.getAverageRating())
            .member(member)
            .images(imageDtos)
            .tags(tagDtos)
            .schedules(scheduleDtos)
            .build();
    }

}
