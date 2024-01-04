package com.haejwo.tripcometrue.domain.triprecordreview.repository;

import com.haejwo.tripcometrue.domain.triprecordreview.entity.TripRecordReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRecordReviewRepository extends JpaRepository<TripRecordReview, Long> {
}
