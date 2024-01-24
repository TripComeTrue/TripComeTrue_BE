package com.haejwo.tripcometrue.domain.review.triprecordreview.entity;

import com.haejwo.tripcometrue.domain.likes.entity.TripRecordReviewLikes;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.RegisterTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.haejwo.tripcometrue.domain.review.global.PointType.ONLY_ONE_POINT;
import static com.haejwo.tripcometrue.domain.review.global.PointType.TWO_POINTS;
import static jakarta.persistence.CascadeType.REMOVE;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordReview extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "trip_record_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_record_id")
    private TripRecord tripRecord;

    @OneToMany(mappedBy = "tripRecordReview", cascade = REMOVE, orphanRemoval = true)
    private List<TripRecordReviewLikes> tripRecordReviewLikeses = new ArrayList<>();

    @NotNull
    private Float ratingScore;

    @Lob
    private String content;

    private Integer likeCount;
    private String imageUrl;
    private boolean hasAnyRegisteredPhotoUrl;

    @Builder
    public TripRecordReview(
            Member member, TripRecord tripRecord, String content,
            Float ratingScore, Integer likeCount, String imageUrl,
            boolean hasAnyRegisteredPhotoUrl
    ) {
        this.member = member;
        this.tripRecord = tripRecord;
        this.content = content;
        this.ratingScore = ratingScore;
        this.likeCount = likeCount;
        this.imageUrl = imageUrl;
        this.hasAnyRegisteredPhotoUrl = hasAnyRegisteredPhotoUrl;
    }

    public void registerContent(RegisterTripRecordReviewRequestDto requestDto, Member member) {
        this.content = requestDto.content();

        if (requestDto.imageUrl() != null) {
            this.imageUrl = requestDto.imageUrl();
            member.earnPoint(TWO_POINTS.getPoint());
            hasAnyRegisteredPhotoUrl = true;
            return;
        }

        member.earnPoint(ONLY_ONE_POINT.getPoint());
    }

    public void update(ModifyTripRecordReviewRequestDto requestDto, Member member) {
        this.ratingScore = requestDto.ratingScore();
        this.content = requestDto.content();

        if (!hasAnyRegisteredPhotoUrl && requestDto.imageUrl() != null) {
            member.earnPoint(ONLY_ONE_POINT.getPoint());
            hasAnyRegisteredPhotoUrl = true;
        }
        this.imageUrl = requestDto.imageUrl();
    }

    public void increaseLikesCount() {
        this.likeCount += 1;
    }

    public void decreaseLikesCount() {
        this.likeCount -= 1;
    }

    @PrePersist
    private void init() {
        likeCount = 0;
    }
}
