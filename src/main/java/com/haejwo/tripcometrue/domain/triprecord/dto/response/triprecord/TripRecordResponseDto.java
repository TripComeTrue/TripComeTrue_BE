package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;
import lombok.Builder;

public record TripRecordResponseDto(
    Long id,
    String title,
    String content,
    ExpenseRangeType expenseRangeType,
    String countries,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
    Integer totalDays,
    Integer viewCount,
    Double averageRating,
    Long memberId

) {

    @Builder
    public TripRecordResponseDto(Long id, String title, String content,
        ExpenseRangeType expenseRangeType, String countries, LocalDate tripStartDay, LocalDate tripEndDay,
        Integer totalDays, Integer viewCount, Double averageRating, Long memberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expenseRangeType = expenseRangeType;
        this.countries = countries;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.totalDays = totalDays;
        this.viewCount = viewCount;
        this.averageRating = averageRating;
        this.memberId = memberId;
    }

    public static TripRecordResponseDto fromEntity(TripRecord entity) {
        return TripRecordResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .expenseRangeType(entity.getExpenseRangeType())
            .countries(entity.getCountries())
            .tripStartDay(entity.getTripStartDay())
            .tripEndDay(entity.getTripEndDay())
            .totalDays(entity.getTotalDays())
            .viewCount(entity.getViewCount())
            .averageRating(entity.getAverageRating())
            .memberId(entity.getMember().getId())
            .build();
    }

}
