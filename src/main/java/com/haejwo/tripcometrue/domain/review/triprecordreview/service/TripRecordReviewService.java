package com.haejwo.tripcometrue.domain.review.triprecordreview.service;

import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
import com.haejwo.tripcometrue.domain.member.exception.UserNotFoundException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.DeleteTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.EvaluateTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.RegisterTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.EvaluateTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.delete.DeleteAllSuccessTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.delete.DeleteSomeFailureTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.delete.DeleteTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest.EmptyTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest.LatestReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.latest.LatestTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.*;
import com.haejwo.tripcometrue.domain.review.triprecordreview.repository.TripRecordReviewRepository;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripRecordReviewService {

    private final TripRecordReviewRepository tripRecordReviewRepository;
    private final TripRecordRepository tripRecordRepository;
    private final MemberRepository memberRepository;

    // FIXME: 1/18/24 ratingScore @NotNull과 상충되는 부분 수정하기
    @Transactional
    public EvaluateTripRecordReviewResponseDto saveRatingScore(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            EvaluateTripRecordReviewRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecord tripRecord = getTripRecordById(tripRecordId);

        isAlreadyTripRecordReviewExists(loginMember, tripRecord);

        return EvaluateTripRecordReviewResponseDto
                .fromEntity(tripRecordReviewRepository.save(requestDto.toEntity(loginMember, tripRecord)));
    }

    private Member getMember(PrincipalDetails principalDetails) {
        return memberRepository.findById(principalDetails.getMember().getId())
                .orElseThrow(UserNotFoundException::new);
    }

    private void isAlreadyTripRecordReviewExists(Member member, TripRecord tripRecord) {
        if (tripRecordReviewRepository.existsByMemberAndTripRecord(member, tripRecord)) {
            throw new TripRecordReviewAlreadyExistsException();
        }
    }

    private TripRecord getTripRecordById(Long tripRecordId) {
        return tripRecordRepository.findById(tripRecordId)
                .orElseThrow(TripRecordNotFoundException::new);
    }

    // FIXME: 1/18/24 ratingScore @NotNull과 상충되는 부분 수정하기
    @Transactional
    public void modifyTripRecordReview(
            PrincipalDetails principalDetails,
            Long tripRecordReviewId,
            ModifyTripRecordReviewRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecordReview tripRecordReview = getTripRecordReviewById(tripRecordReviewId);

        validateRightMemberAccess(loginMember, tripRecordReview);
        isContentAlreadyRegistered(tripRecordReview);

        tripRecordReview.update(requestDto, loginMember);
    }

    private TripRecordReview getTripRecordReviewById(Long tripRecordReviewId) {
        return tripRecordReviewRepository.findById(tripRecordReviewId)
                .orElseThrow(TripRecordReviewNotFoundException::new);
    }

    private void validateRightMemberAccess(Member member, TripRecordReview tripRecordReview) {
        if (!Objects.equals(tripRecordReview.getMember().getId(), member.getId())) {
            throw new UserInvalidAccessException();
        }
    }

    private void isContentAlreadyRegistered(TripRecordReview tripRecordReview) {
        if (tripRecordReview.getContent() == null) {
            throw new ContentNotInitializedException();
        }
    }

    private boolean hasLikedTripRecordReview(PrincipalDetails principalDetails, TripRecordReview tripRecordReview) {
        List<Long> memberIds = tripRecordReview.getTripRecordReviewLikeses().stream()
                .map(TripRecordReviewLikes::getMember)
                .map(Member::getId)
                .toList();
        return memberIds.contains(principalDetails.getMember().getId());
    }

    @Transactional
    public void registerContent(
            PrincipalDetails principalDetails,
            Long tripRecordReviewId,
            RegisterTripRecordReviewRequestDto requestDto
    ) {

        Member loginMember = getMember(principalDetails);
        TripRecordReview tripRecordReview = getTripRecordReviewById(tripRecordReviewId);

        validateRightMemberAccess(loginMember, tripRecordReview);
        isReviewAlreadyRegister(tripRecordReview);

        tripRecordReview.registerContent(requestDto, loginMember);
    }

    private void isReviewAlreadyRegister(TripRecordReview tripRecordReview) {
        if (tripRecordReview.getContent() != null) {
            throw new DuplicateTripRecordReviewException();
        }
    }

//    private void calculateAndSavePoints(TripRecordReview tripRecordReview, Member member) {
//        int point = isImageIncluded(tripRecordReview) ? CONTENT_WITH_IMAGE_POINT.getPoint() : ONLY_CONTENT_POINT.getPoint();
//        member.earnPoint(point);
//    }
//
//    private boolean isImageIncluded(TripRecordReview tripRecordReview) {
//        return tripRecordReview.getImageUrl() != null;
//    }

    public TripRecordReviewListResponseDto getMyTripRecordReviewList(
            PrincipalDetails principalDetails,
            Pageable pageable
    ) {

        Page<TripRecordReview> reviews =
                tripRecordReviewRepository.findByMember(getMember(principalDetails), pageable);

        List<TripRecordReviewResponseDto> responseDtos = reviews.stream()
                .map(tripRecordReview -> TripRecordReviewResponseDto.fromEntity(
                        tripRecordReview,
                        hasLikedTripRecordReview(principalDetails, tripRecordReview))
                ).toList();

        return TripRecordReviewListResponseDto.fromResponseDtos(reviews.getTotalElements(), responseDtos);
    }

    @Transactional
    public DeleteTripRecordReviewResponseDto deleteTripRecordReviews(
            DeleteTripRecordReviewRequestDto requestDto
    ) {

        List<Long> tripRecordReviewIds = requestDto.tripRecordReviewIds();
        List<Long> failedIds = new ArrayList<>();

        tripRecordReviewIds.forEach(tripRecordReviewId -> {
            if (tripRecordReviewRepository.existsById(tripRecordReviewId)) {
                tripRecordReviewRepository.deleteById(tripRecordReviewId);
            } else {
                failedIds.add(tripRecordReviewId);
            }
        });

        if (isDeleteAllFail(tripRecordReviewIds, failedIds)) {
            throw new TripRecordReviewDeleteAllFailureException();
        }
        if (!failedIds.isEmpty()) {
            return new DeleteSomeFailureTripRecordReviewResponseDto(failedIds);
        }

        return new DeleteAllSuccessTripRecordReviewResponseDto();
    }

    private boolean isDeleteAllFail(List<Long> tripRecordReviewIds, List<Long> failedIds) {
        return tripRecordReviewIds.size() == failedIds.size();
    }

    public LatestReviewResponseDto getLatestReview(PrincipalDetails principalDetails, Long tripRecordId) {

        getTripRecordById(tripRecordId);
        Member loginMember = getMember(principalDetails);

        Long totalCount = tripRecordReviewRepository.countByTripRecordId(tripRecordId);
        Float myScore = tripRecordReviewRepository
                .findMyScoreByMemberAndTripRecordId(loginMember, tripRecordId).orElse(0f);

        Optional<TripRecordReview> latestReview = tripRecordReviewRepository
                .findTopByTripRecordIdOrderByCreatedAtDesc(tripRecordId);

        if (latestReview.isEmpty()) {
            return EmptyTripRecordReviewResponseDto.fromData(totalCount, myScore);
        }
        return LatestTripRecordReviewResponseDto.fromEntity(totalCount, latestReview.get(), myScore);
    }
}
