package com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteSomeFailurePlaceReviewResponseDto implements DeletePlaceReviewResponseDto {

    private final String errorMessage = "삭제에 실패한 여행지 리뷰(들)이 존재합니다.";
    private final List<Long> failedPlaceReviewIds;

    public DeleteSomeFailurePlaceReviewResponseDto(List<Long> failedIds) {
        this.failedPlaceReviewIds = failedIds;
    }
}
