package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import java.time.LocalDate;
import lombok.Builder;

public record TripRecordRequestDto(
    String title,
    String content,
    ExpenseRangeType expenseRangeType,
    String countries,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
    Member member
) {

    @Builder
    public TripRecordRequestDto(String title, String content, ExpenseRangeType expenseRangeType,
        String countries, LocalDate tripStartDay, LocalDate tripEndDay, Member member) {
        this.title = title;
        this.content = content;
        this.expenseRangeType = expenseRangeType;
        this.countries = countries;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.member = member;
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

    public TripRecord toEntity(Member member) {
        return TripRecord.builder()
            .title(this.title)
            .content(this.content)
            .expenseRangeType(this.expenseRangeType)
            .tripStartDay(this.tripStartDay)
            .tripEndDay(this.tripEndDay)
            .countries(this.countries)
            .member(member)
            .build();
    }

}
