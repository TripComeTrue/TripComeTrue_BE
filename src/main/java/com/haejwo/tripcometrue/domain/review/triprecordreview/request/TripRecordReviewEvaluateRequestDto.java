package com.haejwo.tripcometrue.domain.review.triprecordreview.request;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record TripRecordReviewEvaluateRequestDto(
        // TODO: 1/9/24
        //  평점이 0.5 단위인지 확인하는 로직 추가.
        //  0 ~ 5점 범위 검증. 아니면 예외 발생
        @NotNull(message = "평점은 필수 항목입니다.")
        Short rating

) {
        @Builder
        public TripRecordReviewEvaluateRequestDto(Short rating) {
                this.rating = rating;
        }

        public TripRecordReview toEntity(Member member, TripRecord tripRecord) {
                return TripRecordReview.builder()
                        .member(member)
                        .tripRecord(tripRecord)
                        .rating(this.rating)
                        .build();
        }
}



