package com.haejwo.tripcometrue.domain.review.placereview.service;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.RegisterPlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceReviewService {

    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceRepository placeRepository;

    /*
    여행지 리뷰 등록
     */
    @Transactional
    public RegisterPlaceReviewResponseDto savePlaceReview(
            PrincipalDetails principalDetails,
            Long placeId,
            RegisterPlaceReviewRequestDto requestDto) {

        Place findPlace = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);

        PlaceReview placeReview = RegisterPlaceReviewRequestDto.toEntity(
                principalDetails.getMember(),
                findPlace,
                requestDto);

        PlaceReview savedPlaceReview = placeReviewRepository.save(placeReview);
        return RegisterPlaceReviewResponseDto.fromEntity(savedPlaceReview);
    }

    /*
    여행지에 대한 다수 리뷰 조회
     */


    /*
    여행지에 대한 특정 리뷰 1건 조회
     */

    /*
    여행지에 대한 특정 리뷰 수정
     */

    /*
    여행지에 대한 특정 리뷰 삭제
     */
}
