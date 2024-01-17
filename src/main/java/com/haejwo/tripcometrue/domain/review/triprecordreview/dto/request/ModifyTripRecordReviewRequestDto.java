package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request;

import com.haejwo.tripcometrue.domain.review.triprecordreview.validation.HalfUnit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ModifyTripRecordReviewRequestDto(

        @NotNull(message = "별점은 필수 항목입니다.")
        @DecimalMin(value = "0.5", message = "0.5점 아래로 평가할 순 없습니다.")
        @Max(value = 5, message = "5점을 초과할 순 없습니다.")
        @HalfUnit(message = "별점은 0.5 단위로 입력해야 합니다.")
        Float ratingScore,

        @NotBlank
        @Length(min = 10, max = 2000, message = "작성 허용 범위는 최소 10자 또는 최대 2,000자 입니다.")
        String content,

        String imageUrl

) {

}
