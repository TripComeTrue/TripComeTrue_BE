package com.haejwo.tripcometrue.domain.review.triprecordreview.repository;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripRecordReviewRepositoryCustom {

    Page<TripRecordReview> findByTripRecord(TripRecord tripRecord, Pageable pageable);
}
