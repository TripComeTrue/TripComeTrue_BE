package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.triprecord.entity.ExpenseRangeType;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;
import lombok.Builder;

public record TripRecordRequestDto(
    String title,
    String content,
    ExpenseRangeType expenseRangeType,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
    String countries
) {

    @Builder
    public TripRecordRequestDto(String title, String content, ExpenseRangeType expenseRangeType,
        LocalDate tripStartDay, LocalDate tripEndDay, String countries) {
        this.title = title;
        this.content = content;
        this.expenseRangeType = expenseRangeType;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.countries = countries;
    }

    public TripRecord toEntity() {
        return TripRecord.builder()
            .title(this.title)
            .content(this.content)
            .expenseRangeType(this.expenseRangeType)
            .tripStartDay(this.tripStartDay)
            .tripEndDay(this.tripEndDay)
            .countries(this.countries)
            .build();
    }

}
