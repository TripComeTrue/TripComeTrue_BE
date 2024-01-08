package com.haejwo.tripcometrue.domain.likes.repository;

import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRecordReviewLikesRepository extends JpaRepository {

  Optional<TripRecordReviewLikes> findByMemberIdAndTripRecordReviewId(Long memberId, Long tripRecordReviewId);
}
