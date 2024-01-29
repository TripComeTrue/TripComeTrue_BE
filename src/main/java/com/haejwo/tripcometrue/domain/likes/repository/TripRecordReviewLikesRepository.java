package com.haejwo.tripcometrue.domain.likes.repository;
import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRecordReviewLikesRepository extends JpaRepository<TripRecordReviewLikes,Long> {

  Optional<TripRecordReviewLikes> findByMemberIdAndTripRecordReviewId(Long memberId, Long tripRecordReviewId);
  public boolean existsByMemberAndTripRecordReview(Member member, TripRecordReview tripRecordReview);
}
