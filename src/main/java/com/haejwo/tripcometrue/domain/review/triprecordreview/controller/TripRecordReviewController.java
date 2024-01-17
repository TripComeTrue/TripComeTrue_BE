package com.haejwo.tripcometrue.domain.review.triprecordreview.controller;

import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.EvaluateTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.EvaluateTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.service.TripRecordReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trip-records")
public class TripRecordReviewController {

    private final TripRecordReviewService tripRecordReviewService;

    @PostMapping("/{tripRecordId}/reviews")
    public ResponseEntity<ResponseDTO<EvaluateTripRecordReviewResponseDto>> evaluateTripRecord(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @RequestBody @Valid EvaluateTripRecordReviewRequestDto requestDto
    ) {

        EvaluateTripRecordReviewResponseDto responseDto = tripRecordReviewService.saveTripRecordReview(principalDetails, tripRecordId, requestDto);
        ResponseDTO<EvaluateTripRecordReviewResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
                .status(responseBody.getCode())
                .body(responseBody);
    }

    //todo 리다이렉트가 잘 되는지 확인하기
    //todo 프론트에게 질문 :
    // 1. 리다이렉트를 하면 되는가?
    // 2. 수정된 여행 후기 리뷰 id를 따로 안보내줘도 되는가?
    @PutMapping("/reviews/{tripRecordReviewId}")
    public ResponseEntity<ResponseDTO<Void>> modifyTripRecordReview(
            @PathVariable Long tripRecordReviewId,
            @RequestBody @Valid ModifyTripRecordReviewRequestDto requestDto
    ) {

        tripRecordReviewService.modifyTripRecordReview(tripRecordReviewId, requestDto);
        String redirectUrl = "마이페이지 여행후기 리뷰 리스트 조회 리다이렉트 url";

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", redirectUrl)
                .body(ResponseDTO.ok());
    }

}
