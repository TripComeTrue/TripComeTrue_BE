package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request;

import java.util.List;

public record DeleteTripRecordReviewRequestDto(

        List<Long> tripRecordReviewIds

) {
}
