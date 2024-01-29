package com.haejwo.tripcometrue.domain.review.placereview.repository;

import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReviewRepositoryCustom {

    Page<PlaceReview> findByPlace(Place place, boolean onlyImage, Pageable pageable);
}
