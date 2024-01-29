package com.haejwo.tripcometrue.domain.likes.repository;
import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceReviewLikesRepository extends JpaRepository<PlaceReviewLikes, Long> {

  Optional<PlaceReviewLikes> findByMemberIdAndPlaceReviewId(Long memberId, Long placeReviewId);
  boolean existsByMemberAndPlaceReview(Member member, PlaceReview placeReview);
}
