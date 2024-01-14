package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.store.entity.TripRecordStore;
import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExpenseRangeType;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Integer averageRating;
    private Integer viewCount;


    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordSchedule> tripRecordSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordTag> tripRecordTags = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordImage> tripRecordImages = new ArrayList<>();

    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordStore> tripRecordStores = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public TripRecord(Long id, String title, String content, ExpenseRangeType expenseRangeType,
        String countries, LocalDate tripStartDay, LocalDate tripEndDay, Integer totalDays,
        Integer averageRating, Integer viewCount, List<TripRecordSchedule> tripRecordSchedules,
        List<TripRecordTag> tripRecordTags, List<TripRecordImage> tripRecordImages, Member member) {
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
        this.tripRecordSchedules = tripRecordSchedules;
        this.tripRecordTags = tripRecordTags;
        this.tripRecordImages = tripRecordImages;
        this.member = member;
    }

    public void update(TripRecordRequestDto requestDto) {
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.expenseRangeType = requestDto.expenseRangeType();
        this.tripStartDay = requestDto.tripStartDay();
        this.tripEndDay = requestDto.tripEndDay();
        this.countries = requestDto.countries();
    }

    public void incrementViewCount() {
        if(this.viewCount == null) {
            this.viewCount = 1;
        } else {
            this.viewCount++;
        }
    }

    @PrePersist
    public void prePersist() {
        this.totalDays = calculateTotalDays();
        this.viewCount = 0;
    }

    @PreUpdate
    public void preUpdate() {
        this.totalDays = calculateTotalDays();
    }

    private int calculateTotalDays() {
        if (this.tripStartDay == null || this.tripEndDay == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(this.tripStartDay, this.tripEndDay);
    }
}
