package com.haejwo.tripcometrue.domain.triprecordreview.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Lob
    private String content;

    private Short rating;
    private Integer likeCount;
    private String imageUrl;

    @Builder
    public TripRecordReview(Member member, TripRecord tripRecord, String content, Short rating, Integer likeCount, String imageUrl) {
        this.member = member;
        this.tripRecord = tripRecord;
        this.content = content;
        this.rating = rating;
        this.likeCount = likeCount;
        this.imageUrl = imageUrl;
    }
}
