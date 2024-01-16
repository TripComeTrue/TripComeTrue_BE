package com.haejwo.tripcometrue.domain.review.placereview.repository;

import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReviewRepositoryCustom {

    Page<PlaceReview> findByPlaceId(@Param("placeId") Long placeId, Pageable pageable);
}
