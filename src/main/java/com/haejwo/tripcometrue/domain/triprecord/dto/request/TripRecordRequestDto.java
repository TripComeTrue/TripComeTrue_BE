package com.haejwo.tripcometrue.domain.triprecord.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record TripRecordRequestDto(

    List<TripRecordImageRequestDto> tripRecordImages,
    @NotNull(message = "title은 필수값입니다")
    String title,
    @NotNull(message = "content은 필수값입니다")
    String content,
    ExpenseRangeType expenseRangeType,
    List<String> hashTags,
    @NotNull(message = "countries은 필수값입니다")
    String countries,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripStartDay,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate tripEndDay,
    List<TripRecordScheduleRequestDto> tripRecordSchedules
) {

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
