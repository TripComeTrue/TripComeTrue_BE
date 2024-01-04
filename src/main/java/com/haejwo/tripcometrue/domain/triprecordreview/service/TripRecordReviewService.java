package com.haejwo.tripcometrue.domain.triprecordreview.service;

import com.haejwo.tripcometrue.domain.triprecordreview.repository.TripRecordReviewRepository;
import com.haejwo.tripcometrue.domain.triprecordreview.request.TripRecordReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripRecordReviewService {

    private final TripRecordReviewRepository tripRecordReviewRepository;

    public void saveTripRecordReview(Long recordId, TripRecordReviewRegisterRequest request) {

    }
}
