package com.haejwo.tripcometrue.domain.review.placereview.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.MULTI_STATUS;

import com.haejwo.tripcometrue.domain.review.placereview.dto.request.DeletePlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete.DeletePlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete.DeleteSomeFailurePlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.service.PlaceReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{placeId}/reviews")
    public ResponseEntity<ResponseDTO<Page<PlaceReviewResponseDto>>> getPlaceReviewList(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long placeId,
            @RequestParam(defaultValue = "false") boolean onlyImage,
            Pageable pageable
    ) {

        Page<PlaceReviewResponseDto> responseDtos = placeReviewService.getPlaceReviews(principalDetails, placeId, onlyImage, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDtos));
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
    public ResponseEntity<ResponseDTO<DeletePlaceReviewResponseDto>> removePlaceReview(
            @RequestBody DeletePlaceReviewRequestDto requestDto) {

        DeletePlaceReviewResponseDto responseDto = placeReviewService.deletePlaceReview(requestDto);

        if (responseDto instanceof DeleteSomeFailurePlaceReviewResponseDto) {
            return ResponseEntity
                    .status(MULTI_STATUS)
                    .body(ResponseDTO.errorWithData(MULTI_STATUS, responseDto));
        }

        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping("/reviews/my")
    public ResponseEntity<ResponseDTO<List<PlaceReviewListResponseDto>>> getMyPlaceReviews(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        Pageable pageable
    ) {
        List<PlaceReviewListResponseDto> responseDtos
            = placeReviewService.getMyPlaceReviewsList(principalDetails, pageable);
        ResponseDTO<List<PlaceReviewListResponseDto>> responseBody
            = ResponseDTO.okWithData(responseDtos);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseBody);
    }
}
