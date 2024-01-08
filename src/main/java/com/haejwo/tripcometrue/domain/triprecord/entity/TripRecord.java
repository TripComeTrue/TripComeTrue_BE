package com.haejwo.tripcometrue.domain.triprecord.entity;

import com.haejwo.tripcometrue.domain.triprecord.dto.request.TripRecordRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_record_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    private String content;

    private ExpenseType expenseType;

    private String countries;

    private LocalDate tripStartDay;
    private LocalDate tripEndDay;

    private Integer totalDays;
    private Integer average_rating;
    private Integer viewCount;


    @OneToMany(mappedBy = "tripRecord", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TripRecordSchedule> tripRecordSchedules;

    @Builder
    public TripRecord(Long id, String title, String content, Integer average_rating,
        ExpenseType expenseType, LocalDate tripStartDay, LocalDate tripEndDay, Integer totalDays,
        String countries, Integer viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.average_rating = average_rating;
        this.expenseType = expenseType;
        this.tripStartDay = tripStartDay;
        this.tripEndDay = tripEndDay;
        this.totalDays = totalDays;
        this.countries = countries;
        this.viewCount = viewCount;
    }

    // TODO: update 함수
    public void update(TripRecordRequestDto requestDto) {
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.expenseType = requestDto.expenseType();
        this.tripStartDay = requestDto.tripStartDay();
        this.tripEndDay = requestDto.tripEndDay();
        this.countries = requestDto.countries();
    }

    @PrePersist
    public void prePersist() {
        this.totalDays = calculateTotalDays();
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
