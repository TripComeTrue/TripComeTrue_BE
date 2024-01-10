package com.haejwo.tripcometrue.domain.likes.controller;
import com.haejwo.tripcometrue.domain.likes.dto.response.PlaceReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.dto.response.TripRecordReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.service.LikesService;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

  @RestController
  @RequestMapping("/v1/likes")
  @RequiredArgsConstructor
  public class LikesController {

    private final LikesService likesService;

    @PostMapping("/place-review/{placeReviewId}")
    public ResponseEntity<ResponseDTO<PlaceReviewLikesResponseDto>> likePlaceReview(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long placeReviewId) {

      PlaceReviewLikesResponseDto responseDto = likesService.likePlaceReview(principalDetails, placeReviewId);
      return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @PostMapping("/trip-record-review/{tripRecordReviewId}")
    public ResponseEntity<ResponseDTO<TripRecordReviewLikesResponseDto>> likeTripRecordReview(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long tripRecordReviewId) {

      TripRecordReviewLikesResponseDto responseDto = likesService.likeTripRecordReview(principalDetails, tripRecordReviewId);
      return ResponseEntity.ok(ResponseDTO.okWithData(responseDto));
    }

    @DeleteMapping("/place-review/{placeReviewId}")
    public ResponseEntity<ResponseDTO<Void>> unlikePlaceReview(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long placeReviewId) {

      likesService.unlikePlaceReview(principalDetails, placeReviewId);
      return ResponseEntity.ok(ResponseDTO.ok());
    }

    @DeleteMapping("/trip-record-review/{tripRecordReviewId}")
    public ResponseEntity<ResponseDTO<Void>> unlikeTripRecordReview(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long tripRecordReviewId) {

      likesService.unlikeTripRecordReview(principalDetails, tripRecordReviewId);
      return ResponseEntity.ok(ResponseDTO.ok());
    }
  }


