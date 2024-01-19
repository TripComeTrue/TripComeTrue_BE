package com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.delete;

import lombok.Getter;

@Getter
public class DeleteAllSuccessTripRecordReviewResponseDto implements DeleteTripRecordReviewResponseDto {

    private final String successMessage = "성공적으로 모든 여행 후기 리뷰(들)을 삭제했습니다.";
}
