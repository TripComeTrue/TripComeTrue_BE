package com.haejwo.tripcometrue.domain.review.placereview.controller;

import com.haejwo.tripcometrue.domain.review.placereview.dto.request.RegisterPlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.service.PlaceReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trip-places")
public class PlaceReviewController {

    private final PlaceReviewService placeReviewService;

    @PostMapping("/{tripPlaceId}/reviews")
    public ResponseEntity<ResponseDTO<RegisterPlaceReviewResponseDto>> registerPlaceReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripPlaceId,
            @RequestBody RegisterPlaceReviewRequestDto requestDto) {

        RegisterPlaceReviewResponseDto responseDto = placeReviewService.savePlaceReview(principalDetails, tripPlaceId, requestDto);
        ResponseDTO<RegisterPlaceReviewResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
                .status(responseBody.getCode())
                .body(responseBody);
    }
}
