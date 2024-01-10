package com.haejwo.tripcometrue.domain.review.triprecordreview.service;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.repository.TripRecordReviewRepository;
import com.haejwo.tripcometrue.domain.review.triprecordreview.request.TripRecordReviewEvaluateRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.response.TripRecordReviewEvaluateResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.repository.TripRecordRepository;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TripRecordReviewService {

    private final TripRecordReviewRepository tripRecordReviewRepository;
    private final TripRecordRepository tripRecordRepository;

    /**
     * 여행 후기에 대해 별점만 DB에 저장
     * @return 저장한 리뷰 식별자를 반환
     */
    @Transactional
    public TripRecordReviewEvaluateResponseDto saveTripRecordReview(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            TripRecordReviewEvaluateRequestDto request) {

        Member member = principalDetails.getMember();
        TripRecord tripRecord = tripRecordRepository.findById(tripRecordId)
                .orElseThrow(IllegalAccessError::new);//여행 후기 없는 예외 던지기

        TripRecordReview tripRecordReview = request.toEntity(member, tripRecord);
        TripRecordReview savedTripRecordReview = tripRecordReviewRepository.save(tripRecordReview);

        return TripRecordReviewEvaluateResponseDto
                .fromEntity(savedTripRecordReview);
    }
}
