package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.review.triprecordreview.validation.HalfUnit;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record EvaluateTripRecordReviewRequestDto(

        @NotNull(message = "별점은 필수 항목입니다.")
        @DecimalMin(value = "0.5", message = "0.5점 아래로 평가할 순 없습니다.")
        @Max(value = 5, message = "5점을 초과할 순 없습니다.")
        @HalfUnit(message = "별점은 0.5 단위로 입력해야 합니다.")
        Float ratingScore

) {

    public TripRecordReview toEntity(Member member, TripRecord tripRecord) {
        return TripRecordReview.builder()
                .member(member)
                .tripRecord(tripRecord)
                .ratingScore(this.ratingScore)
                .build();
    }
}



