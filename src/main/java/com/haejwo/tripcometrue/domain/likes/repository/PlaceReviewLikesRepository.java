package com.haejwo.tripcometrue.domain.likes.repository;
import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceReviewLikesRepository extends JpaRepository<PlaceReviewLikes, Long> {

  Optional<PlaceReviewLikes> findByMemberIdAndPlaceReviewId(Long memberId, Long placeReviewId);
}
