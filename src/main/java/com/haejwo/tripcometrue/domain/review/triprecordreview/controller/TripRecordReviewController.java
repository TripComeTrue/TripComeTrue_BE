package com.haejwo.tripcometrue.domain.review.triprecordreview.controller;

import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.DeleteTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.EvaluateTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.RegisterTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.EvaluateTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.SimpleTripRecordResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.delete.DeleteTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest.LatestReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.service.TripRecordReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

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

        EvaluateTripRecordReviewResponseDto responseDto =
                tripRecordReviewService.saveRatingScore(principalDetails, tripRecordId, requestDto);
        return ResponseEntity
                .status(CREATED)
                .body(ResponseDTO.successWithData(CREATED, responseDto));
    }

    @PutMapping("/reviews/{tripRecordReviewId}")
    public ResponseEntity<ResponseDTO<Void>> modifyReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordReviewId,
            @RequestBody @Valid ModifyTripRecordReviewRequestDto requestDto
    ) {

        tripRecordReviewService.modifyTripRecordReview(principalDetails, tripRecordReviewId, requestDto);
        return ResponseEntity.ok().body(ResponseDTO.ok());
    }

    @PutMapping("/reviews/{tripRecordReviewId}/contents")
    public ResponseEntity<ResponseDTO<Void>> registerReviewContent(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordReviewId,
            @RequestBody @Valid RegisterTripRecordReviewRequestDto requestDto
    ) {

        tripRecordReviewService.registerContent(principalDetails, tripRecordReviewId, requestDto);
        return ResponseEntity.ok(ResponseDTO.ok());
    }

    @GetMapping("/{tripRecordId}/reviews/latest")
    public ResponseEntity<ResponseDTO<LatestReviewResponseDto>> getLatestReviewAndMyScore(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId
    ) {

        LatestReviewResponseDto responseDto =
                tripRecordReviewService.getLatestReview(principalDetails, tripRecordId);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping("/reviews/{tripRecordReviewId}")
    public ResponseEntity<ResponseDTO<SimpleTripRecordResponseDto>> getTripRecordReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordReviewId
    ) {

        SimpleTripRecordResponseDto responseDto =
                tripRecordReviewService.getTripRecordReview(principalDetails, tripRecordReviewId);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<ResponseDTO<DeleteTripRecordReviewResponseDto>> removeTripRecordReviews(
            @RequestBody DeleteTripRecordReviewRequestDto requestDto
    ) {

        DeleteTripRecordReviewResponseDto responseDto =
                tripRecordReviewService.deleteTripRecordReviews(requestDto);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping("/{tripRecordId}/reviews")
    public ResponseEntity<ResponseDTO<TripRecordReviewListResponseDto>> getTripRecordReviews(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordId,
            Pageable pageable
    ) {

        TripRecordReviewListResponseDto responseDto =
                tripRecordReviewService.getTripRecordReviewList(principalDetails, tripRecordId, pageable);
        return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @GetMapping("/reviews/my")
    public ResponseEntity<ResponseDTO<TripRecordReviewListResponseDto>> getMyTripRecordReviews(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Pageable pageable
    ) {

        TripRecordReviewListResponseDto responseDtos =
                tripRecordReviewService.getMyTripRecordReviewList(principalDetails, pageable);
        return ResponseEntity.ok().body(ResponseDTO.okWithData(responseDtos));
    }
}
