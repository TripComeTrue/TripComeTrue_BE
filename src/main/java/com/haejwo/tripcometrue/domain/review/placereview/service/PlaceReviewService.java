package com.haejwo.tripcometrue.domain.review.placereview.service;

import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewNotFoundException;
import com.haejwo.tripcometrue.domain.review.placereview.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceReviewService {

    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

    private static final int ONLY_CONTENT_POINT = 1;
    private static final int CONTENT_WITH_IMAGE_POINT = 2;

    //todo 본문 validation 추가
    //todo 동일한 여행지 리뷰 작성시 예외 발생시키기
    /*
    여행지 리뷰 등록
     */
    @Transactional
    public RegisterPlaceReviewResponseDto savePlaceReview(
            PrincipalDetails principalDetails,
            Long placeId,
            PlaceReviewRequestDto requestDto) {

        Member find = principalDetails.getMember();
        Member member = em.merge(find);  //영속성 컨텍스트에 넣기

        PlaceReview placeReview = PlaceReviewRequestDto.toEntity(
                member,
                getPlaceById(placeId),
                requestDto);

        calculatePoints(placeReview, member);
        return RegisterPlaceReviewResponseDto.fromEntity(placeReviewRepository.save(placeReview));
    }

    private Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }

    private static void calculatePoints(PlaceReview placeReview, Member member) {
        if (placeReview.getImageUrl() == null) {
            member.earnPoint(ONLY_CONTENT_POINT);
        } else {
            member.earnPoint(CONTENT_WITH_IMAGE_POINT);
        }
    }

    /*
    여행지에 대한 특정 리뷰 단건 조회
     */
    //todo 관련 댓글 리스트로 보내기 추가,
    //     댓글 갯수 추가
    //fixme fetch join이 적용 안되서 n+1 문제 고치기
    public PlaceReviewResponseDto getPlaceReview(PrincipalDetails principalDetails, Long placeReviewId) {

        PlaceReview placeReview = getPlaceReviewById(placeReviewId);
        boolean hasLiked = false;

        if (isLoggedIn(principalDetails)) {
            hasLiked = hasLikedPlaceReview(principalDetails, placeReview);
        }

        return PlaceReviewResponseDto.fromEntity(placeReview, hasLiked);
    }

    private PlaceReview getPlaceReviewById(Long placeReviewId) {
        return placeReviewRepository.findById(placeReviewId)
                .orElseThrow(PlaceReviewNotFoundException::new);
    }

    private boolean isLoggedIn(PrincipalDetails principalDetails) {
        return principalDetails != null;
    }

    private boolean hasLikedPlaceReview(PrincipalDetails principalDetails, PlaceReview placeReview) {
        List<Long> memberIds = placeReview.getPlaceReviewLikeses().stream()
                .map(PlaceReviewLikes::getMember)
                .map(Member::getId)
                .toList();
        return memberIds.contains(principalDetails.getMember().getId());
    }

    /*
    특정 여행지에 대한 다수의 리뷰 조회
     */
    //todo 총 개수를 포함한 페이징 및 정렬 적용 (최신순, 추천순, 사진 필터)
    //댓글은 갯수만 포함
    public void getPlaceReviews(Long placeId) {

    }

    /*
    여행지에 대한 특정 리뷰 수정
     */
    @Transactional
    public PlaceReviewResponseDto modifyPlaceReview(
            PrincipalDetails principalDetails,
            Long placeReviewId,
            PlaceReviewRequestDto requestDto) {

        PlaceReview placeReview = getPlaceReviewById(placeReviewId);
        placeReview.update(requestDto);

        return PlaceReviewResponseDto
                .fromEntity(placeReview, hasLikedPlaceReview(principalDetails, placeReview));
    }

    /*
    여행지에 대한 특정 리뷰 삭제
     */
    //todo 여러 값을 제거하는 로직 추가
    @Transactional
    public void deletePlaceReview(Long tripReviewId) {
        placeReviewRepository.delete(getPlaceReviewById(tripReviewId));
    }
}
