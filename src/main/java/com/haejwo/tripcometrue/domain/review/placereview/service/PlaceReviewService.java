package com.haejwo.tripcometrue.domain.review.placereview.service;

import com.haejwo.tripcometrue.domain.likes.repository.PlaceReviewLikesRepository;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
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
    private final PlaceReviewLikesRepository likesRepository;

    /*
    여행지 리뷰 등록
     */
    @Transactional
    public RegisterPlaceReviewResponseDto savePlaceReview(
            PrincipalDetails principalDetails,
            Long placeId,
            RegisterPlaceReviewRequestDto requestDto) {

        PlaceReview placeReview = RegisterPlaceReviewRequestDto.toEntity(
                principalDetails.getMember(),
                getPlaceById(placeId),
                requestDto);

        PlaceReview savedPlaceReview = placeReviewRepository.save(placeReview);
        return RegisterPlaceReviewResponseDto.fromEntity(savedPlaceReview);
    }

    /*
    여행지에 대한 특정 리뷰 단건 조회
     */
    //todo 사용자가 좋아요 했는지 여부 로직 추가
    //todo 관련 댓글 리스트로 보내기 추가,
    //     댓글 갯수 추가
    //fixme fetch join이 적용 안되서 n+1 문제 고치기
    /*
    1. likeRepository에서 로그인한 멤버와 여행지리뷰가 존재하는지 확인하기 (일단 이거 적용해보자)
    2. 여행지 리뷰가 좋아요를 가지고 있기.(양방향)
     */
    public PlaceReviewResponseDto getPlaceReview(PrincipalDetails principalDetails, Long placeReviewId) {

//     ** 버전 1  -  단반향 매핑
        PlaceReview placeReview = getPlaceReviewById(placeReviewId);
        boolean amILike = false;

        if (isLogin(principalDetails)) {
            amILike = likesRepository.existsByMemberAndPlaceReview(principalDetails.getMember(), placeReview);
        }

        return PlaceReviewResponseDto.fromEntity(placeReview, amILike);


    // ** 버전 2  -  양방향 매핑
//        PlaceReview placeReview = getPlaceReviewById(placeReviewId);
//        boolean amILike = false;
//
//        if (isLogin(principalDetails)) {
//            List<PlaceReviewLike> placeReviewLikes = placeReview.getPlaceReviewLikes();
//            List<Long> ids = placeReviewLikes.stream()
//                    .map(PlaceReviewLike::getPlaceReview)
//                    .map(PlaceReview::getId)
//                    .collect(Collectors.toList());
//            amILike = ids.contains(placeReviewId);
//        }

//        return PlaceReviewResponseDto.fromEntity(placeReview, amILike);
    }

    private boolean isLogin(PrincipalDetails principalDetails) {
        return principalDetails != null;
    }

    private PlaceReview getPlaceReviewById(Long placeReviewId) {
        return placeReviewRepository.findById(placeReviewId)
                .orElseThrow(PlaceReviewNotFoundException::new);
    }

    /*
    특정 여행지에 대한 다수의 리뷰 조회
     */
    //todo 총 개수를 포함한 페이징 및 정렬 적용 (최신순, 추천순, 사진 필터)
    //댓글은 갯수만 포함
//    public PlaceReviewResponseDto getPlaceReviews(Long placeId) {
//        PlaceReview placeReview = placeReviewRepository.findByPlaceId()
//                .orElseThrow(PlaceNotFoundException::new);
//
//    }

    private Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }



    /*
    여행지에 대한 특정 리뷰 수정
     */

    /*
    여행지에 대한 특정 리뷰 삭제
     */
}
