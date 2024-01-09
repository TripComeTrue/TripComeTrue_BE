package com.haejwo.tripcometrue.domain.likes.service;

import com.haejwo.tripcometrue.domain.Review.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.Review.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.Review.repository.PlaceReviewRepository;
import com.haejwo.tripcometrue.domain.Review.repository.TripRecordReviewRepository;
import com.haejwo.tripcometrue.domain.likes.dto.response.PlaceReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.dto.response.TripRecordReviewLikesResponseDto;
import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import com.haejwo.tripcometrue.domain.likes.exception.InvalidLikesException;
import com.haejwo.tripcometrue.domain.likes.repository.PlaceReviewLikesRepository;
import com.haejwo.tripcometrue.domain.likes.repository.TripRecordReviewLikesRepository;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.member.repository.MemberRepository;
import com.haejwo.tripcometrue.global.exception.ErrorCode;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

  @Service
  @RequiredArgsConstructor
  public class LikesServiceImpl implements LikesService {

    private final PlaceReviewLikesRepository placeReviewLikesRepository;
    private final TripRecordReviewLikesRepository tripRecordReviewLikesRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final TripRecordReviewRepository tripRecordReviewRepository;
    private final MemberRepository memberRepository;

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
          .orElseThrow(() -> new EntityNotFoundException());

      PlaceReviewLikes like = PlaceReviewLikes.builder()
          .member(member)
          .placeReview(placeReview)
          .build();
      placeReviewLikesRepository.save(like);

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
          .orElseThrow(() -> new EntityNotFoundException());
      TripRecordReview tripRecordReview = tripRecordReviewRepository.findById(tripRecordReviewId)
          .orElseThrow(() -> new EntityNotFoundException());

      TripRecordReviewLikes like = TripRecordReviewLikes.builder()
          .member(member)
          .tripRecordReview(tripRecordReview)
          .build();
      tripRecordReviewLikesRepository.save(like);

      return TripRecordReviewLikesResponseDto.fromEntity(like);
    }


    @Transactional
    public void unlikePlaceReview(PrincipalDetails principalDetails, Long placeReviewId) {
      Long memberId = principalDetails.getMember().getId();

      PlaceReviewLikes like = placeReviewLikesRepository.findByMemberIdAndPlaceReviewId(memberId, placeReviewId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.LIKES_NOT_FOUND));

      placeReviewLikesRepository.delete(like);
    }

    @Transactional
    public void unlikeTripRecordReview(PrincipalDetails principalDetails, Long tripRecordReviewId) {
      Long memberId = principalDetails.getMember().getId();

      TripRecordReviewLikes like = tripRecordReviewLikesRepository.findByMemberIdAndTripRecordReviewId(memberId, tripRecordReviewId)
          .orElseThrow(() -> new InvalidLikesException(ErrorCode.LIKES_NOT_FOUND));

      tripRecordReviewLikesRepository.delete(like);
    }
  }


