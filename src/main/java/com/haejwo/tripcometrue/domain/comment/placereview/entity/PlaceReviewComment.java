package com.haejwo.tripcometrue.domain.comment.placereview.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.placereview.entity.PlaceReview;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceReviewComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_review_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_review_id")
    private PlaceReview placeReview;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private PlaceReviewComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = REMOVE, orphanRemoval = true)
    private List<PlaceReviewComment> childComments = new ArrayList<>();

    @Column(nullable = false)
    private String content;

    @Builder
    public PlaceReviewComment(Member member, PlaceReview placeReview, PlaceReviewComment parentComment, String content) {
        this.member = member;
        this.placeReview = placeReview;
        this.parentComment = parentComment;
        this.content = content;
    }
}
