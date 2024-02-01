package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.review.triprecordreview.entity.TripRecordReview;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private ExpenseRangeType expenseRangeType;

    private String countries;

    private LocalDate tripStartDay;
    private LocalDate tripEndDay;
    private Integer totalDays;

    private Double averageRating;
    private Integer viewCount;
    private Integer storeCount;
    private Integer reviewCount;
    private Integer commentCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordSchedule> tripRecordSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordTag> tripRecordTags = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordImage> tripRecordImages = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordStore> tripRecordStores = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecordReview", cascade = CascadeType.REMOVE)
    private List<TripRecordReview> tripRecordReviews = new ArrayList<>();

    @Builder
    private TripRecord(Long id, String title, String content, ExpenseRangeType expenseRangeType,
        String countries, LocalDate tripStartDay, LocalDate tripEndDay, Integer totalDays,
        Double averageRating, Integer viewCount, Integer storeCount, Integer reviewCount,
        Integer commentCount, List<TripRecordSchedule> tripRecordSchedules,
        List<TripRecordTag> tripRecordTags, List<TripRecordImage> tripRecordImages,
        List<TripRecordStore> tripRecordStores, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expenseRangeType = expenseRangeType;
        this.countries = countries;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.totalDays = totalDays;
        this.averageRating = averageRating;
        this.viewCount = viewCount;
        this.storeCount = storeCount;
        this.reviewCount = reviewCount;
        this.commentCount = commentCount;
        this.tripRecordSchedules = tripRecordSchedules;
        this.tripRecordTags = tripRecordTags;
        this.tripRecordImages = tripRecordImages;
        this.tripRecordStores = tripRecordStores;
        this.member = member;
    }

    public void update(TripRecordRequestDto requestDto) {
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.expenseRangeType = requestDto.expenseRangeType();
        this.tripStartDay = requestDto.tripStartDay();
        this.tripEndDay = requestDto.tripEndDay();
        this.totalDays = (int)ChronoUnit.DAYS.between(this.tripStartDay, this.tripEndDay)+1;
        this.countries = requestDto.countries();
    }

    public void incrementViewCount() {
        if(this.viewCount == null) {
            this.viewCount = 1;
        } else {
            this.viewCount++;
        }
    }

    public void incrementStoreCount() {
        if(this.storeCount == null) {
            this.storeCount = 1;
        } else {
            this.storeCount++;
        }
    }

    public void decrementStoreCount() {
        if(this.storeCount > 0) {
            this.storeCount--;
        }
    }

    public void incrementReviewCount() {
        if(this.reviewCount == null) {
            this.reviewCount = 1;
        } else {
            this.reviewCount++;
        }
    }

    public void incrementCommentCount() {
        if(this.commentCount == null) {
            this.commentCount = 1;
        } else {
            this.commentCount++;
        }
    }

    public void calculateAverageRating(double ratingScore) {
        if (tripRecordReviews.isEmpty()) {
            averageRating = ratingScore;
        } else {
            double totalRating = averageRating * tripRecordReviews.size();
            totalRating += ratingScore;
            averageRating = totalRating / (tripRecordReviews.size() + 1);
        }
    }

    public void decreaseCommentCount(int count) {
        this.commentCount -= count;
    }

    @PrePersist
    public void prePersist() {
        this.totalDays = calculateTotalDays();
        this.viewCount = 0;
        this.storeCount = 0;
        this.reviewCount = 0;
        this.commentCount = 0;
    }

    @PreUpdate
    public void preUpdate() {
        this.totalDays = calculateTotalDays();
    }

    private int calculateTotalDays() {
        if (this.tripStartDay == null || this.tripEndDay == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(this.tripStartDay, this.tripEndDay)+1;
    }
}
