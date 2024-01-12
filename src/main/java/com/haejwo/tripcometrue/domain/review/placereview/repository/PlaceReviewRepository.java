package com.haejwo.tripcometrue.domain.review.placereview.repository;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long>, PlaceReviewRepositoryCustom {

    boolean existsByMemberAndPlace(Member member, Place place);

  List<PlaceReview> findByMemberId(Long memberId, Pageable pageable);
}
