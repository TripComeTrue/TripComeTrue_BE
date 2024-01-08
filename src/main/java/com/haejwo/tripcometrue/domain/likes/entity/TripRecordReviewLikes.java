package com.haejwo.tripcometrue.domain.likes.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecordReviewLikes extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trip_record_review_likes_id")
  private Long Id;

  @ManyToOne
  @JoinColumn(name = "trip_record_review_id")
  private TripRecordReview tripRecordReview;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;


  @Builder
  public TripRecordReviewLikes(Member member, TripRecordReview tripRecordReview) {
    this.member = member;
    this.tripRecordReview = tripRecordReview;
  }



}
