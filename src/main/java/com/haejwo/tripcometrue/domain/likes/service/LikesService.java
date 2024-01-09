package com.haejwo.tripcometrue.domain.likes.service;

import com.haejwo.tripcometrue.domain.likes.dto.response.PlaceReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.dto.response.TripRecordReviewLikesResponseDto;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;

public interface LikesService {

    PlaceReviewLikesResponseDto likePlaceReview(PrincipalDetails principalDetails, Long placeReviewId);

    TripRecordReviewLikesResponseDto likeTripRecordReview(PrincipalDetails principalDetails, Long tripRecordReviewId);

    void unlikePlaceReview(PrincipalDetails principalDetails, Long placeReviewId);

    void unlikeTripRecordReview(PrincipalDetails principalDetails, Long tripRecordReviewId);

  }

