package com.haejwo.tripcometrue.domain.review.placereview.controller;

import com.haejwo.tripcometrue.domain.review.placereview.dto.request.DeletePlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.service.PlaceReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/places")
public class PlaceReviewController {

    private final PlaceReviewService placeReviewService;

    @PostMapping("/{placeId}/reviews")
    public ResponseEntity<ResponseDTO<RegisterPlaceReviewResponseDto>> registerPlaceReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeId,
            @RequestBody @Validated PlaceReviewRequestDto requestDto) {

        RegisterPlaceReviewResponseDto responseDto = placeReviewService
                .savePlaceReview(principalDetails, placeId, requestDto);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping("/reviews/{placeReviewId}")
    public ResponseEntity<ResponseDTO<PlaceReviewResponseDto>> getOnePlaceReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeReviewId) {

        PlaceReviewResponseDto responseDto = placeReviewService
                .getPlaceReview(principalDetails, placeReviewId);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @PutMapping("/reviews/{placeReviewId}")
    public ResponseEntity<ResponseDTO<PlaceReviewResponseDto>> modifyPlaceReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeReviewId,
            @RequestBody @Validated PlaceReviewRequestDto requestDto) {

        PlaceReviewResponseDto responseDto = placeReviewService
                .modifyPlaceReview(principalDetails, placeReviewId, requestDto);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<ResponseDTO<Void>> removePlaceReview(
            @RequestBody DeletePlaceReviewRequestDto requestDto) {

        placeReviewService.deletePlaceReview(requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }
}
