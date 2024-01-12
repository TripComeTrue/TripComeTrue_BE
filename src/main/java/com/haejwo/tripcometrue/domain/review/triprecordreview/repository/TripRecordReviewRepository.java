package com.haejwo.tripcometrue.domain.review.triprecordreview.repository;

import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRecordReviewRepository extends JpaRepository<TripRecordReview, Long> {

  List<TripRecordReview> findByMemberId(Long memberId, Pageable pageable);
}
