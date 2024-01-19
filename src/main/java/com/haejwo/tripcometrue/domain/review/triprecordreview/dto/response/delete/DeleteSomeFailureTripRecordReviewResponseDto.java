package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.delete;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteSomeFailureTripRecordReviewResponseDto implements DeleteTripRecordReviewResponseDto {

    private final String errorMessage = "삭제에 실패한 여행 후기 리뷰(들)이 존재합니다.";
    private final List<Long> failedTripRecordReviewIds;

    public DeleteSomeFailureTripRecordReviewResponseDto(List<Long> failedIds) {
        this.failedTripRecordReviewIds = failedIds;
    }
}
