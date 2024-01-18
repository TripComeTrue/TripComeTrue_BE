package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterTripRecordReviewRequestDto(

        @NotBlank
        @Length(min = 10, max = 2000, message = "작성 허용 범위는 최소 10자 또는 최대 2,000자 입니다.")
        String content,

        String imageUrl

) {

}
