package com.haejwo.tripcometrue.domain.likes.entity;
import com.haejwo.tripcometrue.domain.Review.entity.PlaceReview;
import com.haejwo.tripcometrue.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceReviewLikes  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "place_review_likes_id")
  private Long Id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "place_review_id")
  private PlaceReview placeReview;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "member_id")
  private Member member;


  @Builder
  public PlaceReviewLikes(Member member, PlaceReview placeReview) {
    this.member = member;
    this.placeReview = placeReview;
  }
}
