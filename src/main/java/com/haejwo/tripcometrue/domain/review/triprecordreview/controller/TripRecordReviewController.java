package com.haejwo.tripcometrue.domain.review.triprecordreview.controller;

import com.haejwo.tripcometrue.domain.review.triprecordreview.response.TripRecordReviewEvaluateResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.request.TripRecordReviewEvaluateRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.service.TripRecordReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TripRecordReviewController {

    private final TripRecordReviewService tripRecordReviewService;

    @PostMapping("/v1/trip-records/{tripRecordId}/reviews")
    public ResponseEntity<ResponseDTO<TripRecordReviewEvaluateResponseDto>> evaluateTripRecord(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            @RequestBody @Valid TripRecordReviewEvaluateRequestDto request) {

        TripRecordReviewEvaluateResponseDto responseDto = tripRecordReviewService.saveTripRecordReview(principalDetails, tripRecordId, request);
        ResponseDTO<TripRecordReviewEvaluateResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
                .status(responseBody.getCode())
                .body(responseBody);
    }

}
