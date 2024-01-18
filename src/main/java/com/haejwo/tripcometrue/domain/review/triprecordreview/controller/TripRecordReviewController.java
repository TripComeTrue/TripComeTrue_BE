package com.haejwo.tripcometrue.domain.review.triprecordreview.controller;

import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.EvaluateTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.EvaluateTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.service.TripRecordReviewService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        EvaluateTripRecordReviewResponseDto responseDto = tripRecordReviewService
                .saveTripRecordReview(principalDetails, tripRecordId, requestDto);
        ResponseDTO<EvaluateTripRecordReviewResponseDto> responseBody = ResponseDTO.okWithData(responseDto);

        return ResponseEntity
                .status(responseBody.getCode())
                .body(responseBody);
    }

    //todo 로그인한 멤버가 작성한 사람인지 확인하는 로직 추가하기
    @PutMapping("/reviews/{tripRecordReviewId}")
    public ResponseEntity<ResponseDTO<Void>> modifyTripRecordReview(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long tripRecordReviewId,
            @RequestBody @Valid ModifyTripRecordReviewRequestDto requestDto
    ) {
        tripRecordReviewService.modifyTripRecordReview(principalDetails, tripRecordReviewId, requestDto);
        return ResponseEntity.ok().body(ResponseDTO.ok());
    }

//    //단건 조회일 시 좋아요 정보 x
//    //리뷰 수정시 필요한 API
//    //todo 로그인한 사람이 글 주인인지 확인하는 로직 추가하기
//    @GetMapping("/reviews/{tripRecordReviewId}")
//    public ResponseEntity<ResponseDTO<TripRecordReviewResponseDto>> getTripRecordReview(
//            @AuthenticationPrincipal PrincipalDetails principalDetails,
//            @PathVariable Long tripRecordReviewId
//    ) {
//
//
//    }

    @GetMapping("/reviews/my")
    public ResponseEntity<ResponseDTO<TripRecordReviewListResponseDto>> getMyTripRecordReviews(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Pageable pageable
    ) {
        TripRecordReviewListResponseDto responseDtos = tripRecordReviewService.getMyTripRecordReviewList(
                principalDetails, pageable);

        return ResponseEntity.ok().body(ResponseDTO.okWithData(responseDtos));
    }

//    @DeleteMapping("/reviews")
//    public ResponseEntity<ResponseDTO<Void>>
}
