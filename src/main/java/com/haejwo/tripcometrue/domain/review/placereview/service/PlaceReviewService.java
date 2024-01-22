package com.haejwo.tripcometrue.domain.review.placereview.service;

import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
import com.haejwo.tripcometrue.domain.member.exception.UserNotFoundException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.domain.place.repositroy.PlaceRepository;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.DeletePlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.PlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.RegisterPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete.DeleteAllSuccessPlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete.DeletePlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.dto.response.delete.DeleteSomeFailurePlaceReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewAlreadyExistsException;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewDeleteAllFailureException;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewNotFoundException;
import com.haejwo.tripcometrue.domain.review.placereview.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceReviewService {

    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RegisterPlaceReviewResponseDto savePlaceReview(
            PrincipalDetails principalDetails,
            Long placeId,
            PlaceReviewRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        Place place = getPlaceById(placeId);

        isAlreadyPlaceReviewExists(loginMember, place);

        PlaceReview placeReview = PlaceReviewRequestDto.toEntity(loginMember, place, requestDto);
        placeReview.save(requestDto, loginMember);

        return RegisterPlaceReviewResponseDto
                .fromEntity(placeReviewRepository.save(placeReview));
    }

    private Member getMember(PrincipalDetails principalDetails) {
        return memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    private Place getPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
    }

    private void isAlreadyPlaceReviewExists(Member member, Place place) {
        if (placeReviewRepository.existsByMemberAndPlace(member, place)) {
            throw new PlaceReviewAlreadyExistsException();
        }
    }

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

    @Transactional
    public PlaceReviewResponseDto modifyPlaceReview(
            PrincipalDetails principalDetails,
            Long placeReviewId,
            PlaceReviewRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        PlaceReview placeReview = getPlaceReviewById(placeReviewId);

        validateRightMemberAccess(loginMember, placeReview);
        placeReview.update(requestDto, loginMember);

        return PlaceReviewResponseDto
                .fromEntity(placeReview, hasLikedPlaceReview(principalDetails, placeReview));
    }

    private void validateRightMemberAccess(Member member, PlaceReview placeReview) {
        if (!Objects.equals(placeReview.getMember().getId(), member.getId())) {
            throw new UserInvalidAccessException();
        }
    }

    @Transactional
    public DeletePlaceReviewResponseDto deletePlaceReviews(
            DeletePlaceReviewRequestDto requestDto
    ) {

        List<Long> placeReviewIds = requestDto.placeReviewIds();
        List<Long> failedIds = new ArrayList<>();

        placeReviewIds.forEach(placeReviewId -> {
            if (placeReviewRepository.existsById(placeReviewId)) {
                placeReviewRepository.deleteById(placeReviewId);
            } else {
                failedIds.add(placeReviewId);
            }
        });

        if (isDeleteAllFail(placeReviewIds, failedIds)) {
            throw new PlaceReviewDeleteAllFailureException();
        }
        if (!failedIds.isEmpty()) {
            return new DeleteSomeFailurePlaceReviewResponseDto(failedIds);
        }

        return new DeleteAllSuccessPlaceReviewResponseDto();
    }

    private boolean isDeleteAllFail(List<Long> placeReviewIds, List<Long> failedIds) {
        return placeReviewIds.size() == failedIds.size();
    }

    public PlaceReviewListResponseDto getPlaceReviewList(
            PrincipalDetails principalDetails,
            Long placeId,
            boolean onlyImage,
            Pageable pageable
    ) {

        Place place = getPlaceById(placeId);
        Page<PlaceReview> reviews = placeReviewRepository.findByPlace(place, onlyImage, pageable);

        return PlaceReviewListResponseDto.fromResponseDtos(
                reviews,
                reviews.map(placeReview -> PlaceReviewResponseDto.fromEntity(
                        placeReview,
                        hasLikedPlaceReview(principalDetails, placeReview))
                ).toList());
    }

    public PlaceReviewListResponseDto getMyPlaceReviewList(
            PrincipalDetails principalDetails,
            Pageable pageable
    ) {

        Member loginMember = getMember(principalDetails);
        Page<PlaceReview> reviews = placeReviewRepository.findByMember(loginMember, pageable);

        return PlaceReviewListResponseDto.fromResponseDtos(
                reviews,
                reviews.map(placeReview -> PlaceReviewResponseDto.fromEntity(
                        placeReview,
                        hasLikedPlaceReview(principalDetails, placeReview))
                ).toList());
    }
}
