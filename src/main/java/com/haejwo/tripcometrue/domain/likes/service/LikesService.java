package com.haejwo.tripcometrue.domain.likes.service;
import com.haejwo.tripcometrue.domain.likes.dto.response.PlaceReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.dto.response.TripRecordReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import com.haejwo.tripcometrue.domain.likes.exception.InvalidLikesException;
import com.haejwo.tripcometrue.domain.likes.repository.PlaceReviewLikesRepository;
import com.haejwo.tripcometrue.domain.likes.repository.TripRecordReviewLikesRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.domain.place.exception.PlaceReviewNotFoundException;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.review.placereview.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.review.triprecordreview.repository.TripRecordReviewRepository;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordReviewNotFoundException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

  @Service
  @RequiredArgsConstructor
  public class LikesService {

    private final MemberRepository memberRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final TripRecordReviewRepository tripRecordReviewRepository;
    private final PlaceReviewLikesRepository placeReviewLikesRepository;
    private final TripRecordReviewLikesRepository tripRecordReviewLikesRepository;

    @Transactional
    public PlaceReviewLikesResponseDto likePlaceReview(PrincipalDetails principalDetails, Long placeReviewId) {
      Long memberId = principalDetails.getMember().getId();

      Optional<PlaceReviewLikes> existingLikes = placeReviewLikesRepository.findByMemberIdAndPlaceReviewId(memberId, placeReviewId);
      if (existingLikes.isPresent()) {
        throw new InvalidLikesException(ErrorCode.LIKES_ALREADY_EXIST);
      }

      Member member = memberRepository.findById(memberId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.USER_NOT_FOUND));
      PlaceReview placeReview = placeReviewRepository.findById(placeReviewId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.PLACE_REVIEW_NOT_FOUND));

      PlaceReviewLikes like = PlaceReviewLikes.builder()
          .member(member)
          .placeReview(placeReview)
          .build();
      placeReviewLikesRepository.save(like);
      placeReview.increaseLikesCount();

      return PlaceReviewLikesResponseDto.fromEntity(like);
    }

    @Transactional
    public TripRecordReviewLikesResponseDto likeTripRecordReview(PrincipalDetails principalDetails, Long tripRecordReviewId) {
      Long memberId = principalDetails.getMember().getId();

      Optional<TripRecordReviewLikes> existingLikes = tripRecordReviewLikesRepository.findByMemberIdAndTripRecordReviewId(memberId, tripRecordReviewId);
      if (existingLikes.isPresent()) {
        throw new InvalidLikesException(ErrorCode.LIKES_ALREADY_EXIST);
      }

      Member member = memberRepository.findById(memberId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.USER_NOT_FOUND));
      TripRecordReview tripRecordReview = tripRecordReviewRepository.findById(tripRecordReviewId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.TRIP_RECORD_REVIEW_NOT_FOUND));

      TripRecordReviewLikes like = TripRecordReviewLikes.builder()
          .member(member)
          .tripRecordReview(tripRecordReview)
          .build();
      tripRecordReviewLikesRepository.save(like);
      tripRecordReview.increaseLikesCount();

      return TripRecordReviewLikesResponseDto.fromEntity(like);
    }

    @Transactional
    public void unlikePlaceReview(PrincipalDetails principalDetails, Long placeReviewId) {
      Long memberId = principalDetails.getMember().getId();
      PlaceReview placeReview = findByPlaceReviewId(placeReviewId);

      PlaceReviewLikes like = placeReviewLikesRepository.findByMemberIdAndPlaceReviewId(memberId, placeReviewId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.LIKES_NOT_FOUND));

      placeReviewLikesRepository.delete(like);
      placeReview.decreaseLikesCount();
    }

    private PlaceReview findByPlaceReviewId(Long placeReviewId) {
      return placeReviewRepository.findById(placeReviewId)
          .orElseThrow(PlaceReviewNotFoundException::new);
    }

    @Transactional
    public void unlikeTripRecordReview(PrincipalDetails principalDetails, Long tripRecordReviewId) {
      Long memberId = principalDetails.getMember().getId();
      TripRecordReview tripRecordReview = findByTripRecordReviewId(tripRecordReviewId);

      TripRecordReviewLikes like = tripRecordReviewLikesRepository.findByMemberIdAndTripRecordReviewId(memberId, tripRecordReviewId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.LIKES_NOT_FOUND));

      tripRecordReviewLikesRepository.delete(like);
      tripRecordReview.decreaseLikesCount();
    }

    private TripRecordReview findByTripRecordReviewId(Long tripRecordReviewId) {
      return tripRecordReviewRepository.findById(tripRecordReviewId)
          .orElseThrow(TripRecordReviewNotFoundException::new);
    }
  }


