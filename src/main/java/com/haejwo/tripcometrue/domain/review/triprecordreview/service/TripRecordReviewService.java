package com.haejwo.tripcometrue.domain.review.triprecordreview.service;

import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.exception.UserInvalidAccessException;
import com.haejwo.tripcometrue.domain.member.exception.UserNotFoundException;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.EvaluateTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.RegisterTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.EvaluateTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewListResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.response.TripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.DuplicateTripRecordReviewException;
import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.TripRecordReviewAlreadyExistsException;
import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.TripRecordReviewNotFoundException;
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

import java.util.List;
import java.util.Objects;

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

        tripRecordReview.update(requestDto);
    }

    private static void validateRightMemberAccess(Member member, TripRecordReview tripRecordReview) {
        if (!Objects.equals(tripRecordReview.getMember().getId(), member.getId())) {
            throw new UserInvalidAccessException();
        }
    }

    private TripRecordReview getTripRecordReviewById(Long tripRecordReviewId) {
        return tripRecordReviewRepository.findById(tripRecordReviewId)
                .orElseThrow(TripRecordReviewNotFoundException::new);
    }

    public TripRecordReviewListResponseDto getMyTripRecordReviewList(
            PrincipalDetails principalDetails,
            Pageable pageable
    ) {

        Page<TripRecordReview> reviews = tripRecordReviewRepository
                .findByMember(getMember(principalDetails), pageable);

        List<TripRecordReviewResponseDto> responseDtos = reviews.stream()
                .map(tripRecordReview -> TripRecordReviewResponseDto.fromEntity(
                                tripRecordReview,
                                hasLikedTripRecordReview(principalDetails, tripRecordReview))
                ).toList();

        return TripRecordReviewListResponseDto.fromResponseDtos(reviews.getTotalElements(), responseDtos);
    }

    private boolean hasLikedTripRecordReview(PrincipalDetails principalDetails, TripRecordReview tripRecordReview) {
        List<Long> memberIds = tripRecordReview.getTripRecordReviewLikeses().stream()
                .map(TripRecordReviewLikes::getMember)
                .map(Member::getId)
                .toList();
        return memberIds.contains(principalDetails.getMember().getId());
    }

    @Transactional
    public TripRecordReviewResponseDto registerContent(
            PrincipalDetails principalDetails,
            Long tripRecordReviewId,
            RegisterTripRecordReviewRequestDto requestDto) {

        Member loginMember = getMember(principalDetails);
        TripRecordReview tripRecordReview = getTripRecordReviewById(tripRecordReviewId);

        validateRightMemberAccess(loginMember, tripRecordReview);
        isReviewAlreadyRegister(tripRecordReview);

        tripRecordReview.registerContent(requestDto);
        return TripRecordReviewResponseDto.fromEntity(tripRecordReview, false);
    }

    private void isReviewAlreadyRegister(TripRecordReview tripRecordReview) {
        if (tripRecordReview.getContent() != null) {
            throw new DuplicateTripRecordReviewException();
        }
    }
}
