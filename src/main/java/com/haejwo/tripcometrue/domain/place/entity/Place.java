package com.haejwo.tripcometrue.domain.place.entity;

import com.haejwo.tripcometrue.domain.city.entity.City;
import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDto;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String description;
    private String phoneNumber;
    private LocalTime weekdayOpenTime;
    private LocalTime weekdayCloseTime;
    private LocalTime weekendOpenTime;
    private LocalTime weekendCloseTime;
    private Double latitude;
    private Double longitude;

    private Integer storedCount;
    private Integer reviewCount;
    private Integer commentCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @PrePersist
    public void prePersist() {
        this.storedCount = this.storedCount == null ? 0 : storedCount;
        this.reviewCount = 0;
        this.commentCount = 0;
    }

    @Builder
    public Place(Long id, String name, String address, String description, String phoneNumber,
        LocalTime weekdayOpenTime, LocalTime weekdayCloseTime, LocalTime weekendOpenTime,
        LocalTime weekendCloseTime, Double latitude, Double longitude, Integer storedCount,
        Integer reviewCount, Integer commentCount, City city) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.weekdayOpenTime = weekdayOpenTime;
        this.weekdayCloseTime = weekdayCloseTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storedCount = storedCount;
        this.reviewCount = reviewCount;
        this.commentCount = commentCount;
        this.city = city;
    }

    public void update(PlaceRequestDto requestDto) {
        this.name = requestDto.name();
        this.address = requestDto.address();
        this.description = requestDto.description();
        this.weekdayOpenTime = requestDto.weekdayOpenTime();
        this.weekdayCloseTime = requestDto.weekdayCloseTime();
        this.weekendOpenTime = requestDto.weekendOpenTime();
        this.weekendCloseTime = requestDto.weekendCloseTime();
        this.storedCount = requestDto.storedCount();
    }

    public void incrementStoreCount() {
        if(this.storedCount == null) {
            this.storedCount = 1;
        } else {
            this.storedCount++;
        }
    }

    public void decrementStoreCount() {
        if(this.storedCount > 0) {
            this.storedCount--;
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

}
