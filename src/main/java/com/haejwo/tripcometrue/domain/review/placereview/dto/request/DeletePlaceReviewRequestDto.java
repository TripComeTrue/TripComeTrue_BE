package com.haejwo.tripcometrue.domain.review.placereview.dto.request;

import java.util.List;

public record DeletePlaceReviewRequestDto(

        List<Long> placeReviewIds

) {
}
