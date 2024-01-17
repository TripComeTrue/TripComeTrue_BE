package com.haejwo.tripcometrue.domain.review.triprecordreview.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.dto.request.ModifyTripRecordReviewRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @NotNull
    private Float ratingScore;

    @Lob
    private String content;

    private Integer likeCount;
    private String imageUrl;

    @Builder
    public TripRecordReview(Member member, TripRecord tripRecord, String content, Float ratingScore, Integer likeCount, String imageUrl) {
        this.member = member;
        this.tripRecord = tripRecord;
        this.content = content;
        this.ratingScore = ratingScore;
        this.likeCount = likeCount;
        this.imageUrl = imageUrl;
    }

    public void update(ModifyTripRecordReviewRequestDto requestDto) {
        this.ratingScore = requestDto.ratingScore();
        this.content = requestDto.content();
        this.imageUrl = requestDto.imageUrl();
    }
}
