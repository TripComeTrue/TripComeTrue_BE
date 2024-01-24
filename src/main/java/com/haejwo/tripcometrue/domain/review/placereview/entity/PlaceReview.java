package com.haejwo.tripcometrue.domain.review.placereview.entity;

import com.haejwo.tripcometrue.domain.likes.entity.PlaceReviewLikes;
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
import java.util.List;

import static com.haejwo.tripcometrue.domain.review.global.PointType.ONLY_ONE_POINT;
import static com.haejwo.tripcometrue.domain.review.global.PointType.TWO_POINTS;
import static jakarta.persistence.CascadeType.*;
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

    @OneToMany(mappedBy = "placeReview", cascade = REMOVE, orphanRemoval = true)
    private List<PlaceReviewLikes> placeReviewLikeses = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    private String imageUrl;
    private Integer likeCount;
    private Integer commentCount;
    private boolean hasAnyRegisteredPhotoUrl;

    @Builder
    public PlaceReview(Member member, Place place, String content, String imageUrl) {
        this.member = member;
        this.place = place;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void save(PlaceReviewRequestDto requestDto, Member member) {
        if (requestDto.imageUrl() != null) {
            member.earnPoint(TWO_POINTS.getPoint());
            hasAnyRegisteredPhotoUrl = true;
            return;
        }

        member.earnPoint(ONLY_ONE_POINT.getPoint());
    }

    public void update(PlaceReviewRequestDto requestDto, Member member) {
        this.content = requestDto.content();

        if (!hasAnyRegisteredPhotoUrl && requestDto.imageUrl() != null) {
            member.earnPoint(ONLY_ONE_POINT.getPoint());
            hasAnyRegisteredPhotoUrl = true;
        }
        this.imageUrl = requestDto.imageUrl();
    }

    public void increaseLikesCount() {
        likeCount += 1;
    }

    public void decreaseLikesCount() {
        likeCount -= 1;
    }

    public void increaseCommentCount() {
        commentCount += 1;
    }

    public void decreaseCommentCount(int count) {
        this.commentCount -= count;
    }

    @PrePersist
    private void init() {
        likeCount = 0;
        commentCount = 0;
    }
}
