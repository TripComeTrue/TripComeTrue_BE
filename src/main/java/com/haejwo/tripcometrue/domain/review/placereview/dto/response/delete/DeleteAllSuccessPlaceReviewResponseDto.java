package com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete;

import lombok.Getter;

@Getter
public class DeleteAllSuccessPlaceReviewResponseDto implements DeletePlaceReviewResponseDto {

    private final String successMessage = "성공적으로 모든 여행지 리뷰(들)을 삭제했습니다.";
}
