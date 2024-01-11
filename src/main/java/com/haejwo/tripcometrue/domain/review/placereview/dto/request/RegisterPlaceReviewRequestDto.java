package com.haejwo.tripcometrue.domain.review.placereview.dto.request;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;

public record RegisterPlaceReviewRequestDto(
        String imageUrl,
        String content
) {
    public static PlaceReview toEntity(Member member, Place place, RegisterPlaceReviewRequestDto requestDto) {
        return PlaceReview.builder()
                .member(member)
                .place(place)
                .imageUrl(requestDto.imageUrl)
                .content(requestDto.content)
                .build();
    }
}
