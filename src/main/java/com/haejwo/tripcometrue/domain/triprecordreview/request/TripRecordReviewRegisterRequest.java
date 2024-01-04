package com.haejwo.tripcometrue.domain.triprecordreview.request;

import com.haejwo.tripcometrue.domain.triprecordreview.entity.TripRecordReview;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record TripRecordReviewRegisterRequest(

        @NotNull(message = "내용을 입력해야 합니다.")
        String content,

        @Nullable
        String image
) {

}



