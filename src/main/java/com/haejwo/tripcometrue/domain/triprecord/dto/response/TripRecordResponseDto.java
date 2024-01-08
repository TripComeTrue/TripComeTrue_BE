package com.haejwo.tripcometrue.domain.triprecord.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.triprecord.entity.ExpenseType;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;
import lombok.Builder;

public record TripRecordResponseDto(
    Long id,
    String title,
    String content,
    Integer average_rating,
    ExpenseType expenseType,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
    Integer totalDays,
    String countries,
    Integer viewCount

) {

    @Builder
    public TripRecordResponseDto(Long id, String title, String content, Integer average_rating,
        ExpenseType expenseType, LocalDate tripStartDay, LocalDate tripEndDay, Integer totalDays,
        String countries, Integer viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.average_rating = average_rating;
        this.expenseType = expenseType;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.totalDays = totalDays;
        this.countries = countries;
        this.viewCount = viewCount;
    }


    public static TripRecordResponseDto fromEntity(TripRecord entity) {
        return TripRecordResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .average_rating(entity.getAverage_rating())
            .expenseType(entity.getExpenseType())
            .tripStartDay(entity.getTripStartDay())
            .tripEndDay(entity.getTripEndDay())
            .totalDays(entity.getTotalDays())
            .countries(entity.getCountries())
            .viewCount(entity.getViewCount())
            .build();

    }

}
