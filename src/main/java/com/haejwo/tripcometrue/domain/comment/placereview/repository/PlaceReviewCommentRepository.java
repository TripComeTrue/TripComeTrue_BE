package com.haejwo.tripcometrue.domain.comment.placereview.repository;

import com.haejwo.tripcometrue.domain.comment.placereview.entity.PlaceReviewComment;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PlaceReviewCommentRepository extends JpaRepository<PlaceReviewComment, Long> {

    Slice<PlaceReviewComment> findByPlaceReviewOrderByCreatedAtDesc(PlaceReview placeReview, Pageable pageable);

    @Modifying
    @Query("delete from PlaceReviewComment prc where prc.parentComment.id = :placeReviewCommentId")
    int deleteChildrenByPlaceReviewCommentId(@Param("placeReviewCommentId") Long placeReviewCommentId);

    @Modifying
    @Query("delete from PlaceReviewComment prc where prc.id = :placeReviewCommentId")
    int deleteParentByPlaceReviewCommentId(@Param("placeReviewCommentId") Long placeReviewCommentId);
}
