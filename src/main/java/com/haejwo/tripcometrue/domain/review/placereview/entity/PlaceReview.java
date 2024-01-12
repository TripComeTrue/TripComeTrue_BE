package com.haejwo.tripcometrue.domain.review.placereview.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.place.entity.Place;
import com.haejwo.tripcometrue.domain.review.placereview.dto.request.PlaceReviewRequestDto;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

//    @OneToMany(mappedBy = "placeReview")
//    private List<PlaceReviewLike> placeReviewLikes = new ArrayList<>();

    private String content;
    private String imageUrl;
    private Integer likeCount;

    @Builder
    public PlaceReview(Member member, Place place, String content, String imageUrl) {
        this.member = member;
        this.place = place;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void update(PlaceReviewRequestDto requestDto) {
        this.content = requestDto.content();
        this.imageUrl = requestDto.imageUrl();
    }
}
