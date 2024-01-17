package com.haejwo.tripcometrue.domain.review.triprecordreview.service;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.review.triprecordreview.repository.TripRecordReviewRepository;
import com.haejwo.tripcometrue.domain.review.triprecordreview.request.EvaluateTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.response.EvaluateTripRecordReviewResponseDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.response.TripRecordReviewListResponseDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.repository.triprecord.TripRecordRepository;
import com.haejwo.tripcometrue.global.springsecurity.PrincipalDetails;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public EvaluateTripRecordReviewResponseDto saveTripRecordReview(
            PrincipalDetails principalDetails,
            Long tripRecordId,
            EvaluateTripRecordReviewRequestDto request) {

        Member member = principalDetails.getMember();
        TripRecord tripRecord = tripRecordRepository.findById(tripRecordId)
                .orElseThrow(TripRecordNotFoundException::new);

        TripRecordReview tripRecordReview = request.toEntity(member, tripRecord);
        TripRecordReview savedTripRecordReview = tripRecordReviewRepository.save(tripRecordReview);

        return EvaluateTripRecordReviewResponseDto
                .fromEntity(savedTripRecordReview);
    }

    @Transactional(readOnly = true)
    public List<TripRecordReviewListResponseDto> getMyTripRecordReviewsList(
        PrincipalDetails principalDetails, Pageable pageable) {
        Long memberId = principalDetails.getMember().getId();
        List<TripRecordReview> reviews
            = tripRecordReviewRepository.findByMemberId(memberId, pageable);

        return reviews.stream()
            .map(TripRecordReviewListResponseDto::fromEntity)
            .collect(Collectors.toList());
    }
}
