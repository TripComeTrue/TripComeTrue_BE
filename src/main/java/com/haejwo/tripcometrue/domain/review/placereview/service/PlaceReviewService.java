package com.haejwo.tripcometrue.domain.review.placereview.service;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.RegisterPlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewNotFoundException;
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
    //todo 코드 리팩토링 - 메소드 분리하기, 인라인
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
    특정 여행지에 대한 다수의 리뷰 조회
     */
    //todo 총 개수를 포함한 페이징 및 정렬 적용 (최신순, 추천순, 사진 필터)
    //댓글은 갯수만 포함
//    public PlaceReviewResponseDto getPlaceReviews(Long placeId) {
//        PlaceReview placeReview = placeReviewRepository.findByPlaceId()
//                .orElseThrow(PlaceNotFoundException::new);
//    }

    /*
    여행지에 대한 특정 리뷰 1건 조회
     */
    //todo 사용자가 좋아요 했는지 여부 로직 추가
    //todo 관련 댓글 리스트로 보내기 추가,
    //     댓글 갯수 추가
    //fixme fetch join이 적용 안되서 n+1 문제 발생
    public PlaceReviewResponseDto getPlaceReview(PrincipalDetails principalDetails, Long placeReviewId) {
        return PlaceReviewResponseDto.fromEntity(getPlaceReviewById(placeReviewId));
    }

    private PlaceReview getPlaceReviewById(Long placeReviewId) {
        return placeReviewRepository.findById(placeReviewId)
                .orElseThrow(PlaceReviewNotFoundException::new);
    }

    /*
    여행지에 대한 특정 리뷰 수정
     */

    /*
    여행지에 대한 특정 리뷰 삭제
     */
}
