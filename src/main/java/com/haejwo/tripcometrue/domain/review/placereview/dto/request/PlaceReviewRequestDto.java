package com.haejwo.tripcometrue.domain.review.placereview.dto.request;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PlaceReviewRequestDto(

        String imageUrl,

        @NotBlank
        @Length(min = 10, max = 2000, message = "작성 허용 범위는 최소 10자 또는 최대 2,000자 입니다.")
        String content

) {
    public static PlaceReview toEntity(Member member, Place place, PlaceReviewRequestDto requestDto) {
        return PlaceReview.builder()
                .member(member)
                .place(place)
                .imageUrl(requestDto.imageUrl)
                .content(requestDto.content)
                .build();
    }
}
