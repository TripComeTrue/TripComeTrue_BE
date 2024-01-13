package com.haejwo.tripcometrue.domain.review.placereview.service;

import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.DeletePlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewAlreadyExistsException;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewNotFoundException;
import com.haejwo.tripcometrue.domain.review.placereview.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.haejwo.tripcometrue.domain.review.placereview.entity.PointType.CONTENT_WITH_IMAGE_POINT;
import static com.haejwo.tripcometrue.domain.review.placereview.entity.PointType.ONLY_CONTENT_POINT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceReviewService {

    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceRepository placeRepository;
    private final EntityManager em;

    @Transactional
    public RegisterPlaceReviewResponseDto savePlaceReview(
            PrincipalDetails principalDetails,
            Long placeId,
            PlaceReviewRequestDto requestDto) {

        Member member = getPersistentMember(principalDetails);
        Place place = getPlaceById(placeId);

        isPlaceReviewExists(member, place);

        PlaceReview placeReview = PlaceReviewRequestDto.toEntity(member, place, requestDto);

        calculateAndSavePoints(placeReview, member);

        return RegisterPlaceReviewResponseDto
                .fromEntity(placeReviewRepository.save(placeReview));
    }

    private Member getPersistentMember(PrincipalDetails principalDetails) {
        return em.merge(principalDetails.getMember()); //준영속 상태를 영속 상태로 변경
    }

    private Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }

    private void isPlaceReviewExists(Member member, Place place) {
        if (placeReviewRepository.existsByMemberAndPlace(member, place)) {
            throw new PlaceReviewAlreadyExistsException();
        }
    }

    private static void calculateAndSavePoints(PlaceReview placeReview, Member member) {
        int point = isImageIncluded(placeReview) ? CONTENT_WITH_IMAGE_POINT.getPoint() : ONLY_CONTENT_POINT.getPoint();
        member.earnPoint(point);
    }

    private static boolean isImageIncluded(PlaceReview placeReview) {
        return placeReview.getImageUrl() != null;
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

    @Transactional
    public void deletePlaceReview(DeletePlaceReviewRequestDto requestDto) {
        requestDto.placeReviewIds()
                .forEach(id -> placeReviewRepository.delete((getPlaceReviewById(id))));
    }
}
