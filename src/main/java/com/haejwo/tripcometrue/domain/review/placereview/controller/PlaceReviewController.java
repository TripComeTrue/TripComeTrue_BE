package com.haejwo.tripcometrue.domain.review.placereview.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.MULTI_STATUS;

import com.haejwo.tripcometrue.domain.review.placereview.dto.request.DeletePlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete.DeletePlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.service.PlaceReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

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
            @RequestBody @Validated PlaceReviewRequestDto requestDto
    ) {

        RegisterPlaceReviewResponseDto responseDto = placeReviewService
                .savePlaceReview(principalDetails, placeId, requestDto);
        return ResponseEntity
                .status(CREATED)
                .body(ResponseDTO.successWithData(CREATED, responseDto));
    }

    @GetMapping("/reviews/{placeReviewId}")
    public ResponseEntity<ResponseDTO<PlaceReviewResponseDto>> getOnePlaceReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeReviewId
    ) {

        PlaceReviewResponseDto responseDto = placeReviewService
                .getPlaceReview(principalDetails, placeReviewId);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @PutMapping("/reviews/{placeReviewId}")
    public ResponseEntity<ResponseDTO<PlaceReviewResponseDto>> modifyPlaceReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeReviewId,
            @RequestBody @Validated PlaceReviewRequestDto requestDto
    ) {

        PlaceReviewResponseDto responseDto = placeReviewService
                .modifyPlaceReview(principalDetails, placeReviewId, requestDto);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    //todo : 로그인한 사람이 맞는지 확인
    @DeleteMapping("/reviews")
    public ResponseEntity<ResponseDTO<DeletePlaceReviewResponseDto>> removePlaceReviews(
            @RequestBody DeletePlaceReviewRequestDto requestDto
    ) {

        DeletePlaceReviewResponseDto responseDto =
                placeReviewService.deletePlaceReviews(requestDto);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping("/{placeId}/reviews")
    public ResponseEntity<ResponseDTO<PlaceReviewListResponseDto>> getPlaceReviewList(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeId,
            @RequestParam(defaultValue = "false") boolean onlyImage,
            Pageable pageable
    ) {

        PlaceReviewListResponseDto responseDtos =
                placeReviewService.getPlaceReviewList(principalDetails, placeId, onlyImage, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDtos));
    }

    @GetMapping("/reviews/my")
    public ResponseEntity<ResponseDTO<PlaceReviewListResponseDto>> getMyPlaceReviews(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Pageable pageable
    ) {

        PlaceReviewListResponseDto responseDtos
                = placeReviewService.getMyPlaceReviewList(principalDetails, pageable);
        return ResponseEntity.ok((ResponseDTO.okWithData(responseDtos)));
    }
}
