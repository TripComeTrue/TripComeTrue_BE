package com.haejwo.tripcometrue.domain.place.entity;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDto;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
    private LocalTime weekdayOpenTime;
    private LocalTime weekdayCloseTime;
    private LocalTime weekendOpenTime;
    private LocalTime weekendCloseTime;
    private Integer storedCount;
    private Double latitude;
    private Double longitude;

    // 임시 City 테이블 데이터
    private Long cityId;

    @PrePersist
    public void prePersist() {
        this.storedCount = this.storedCount == null ? 0 : storedCount;
    }

    @Builder
    public Place(
        Long id, String name, String address, String description,
        LocalTime weekdayOpenTime, LocalTime weekdayCloseTime,
        LocalTime weekendOpenTime, LocalTime weekendCloseTime,
        Integer storedCount, Long cityId, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.weekdayOpenTime = weekdayOpenTime;
        this.weekdayCloseTime = weekdayCloseTime;
        this.weekendOpenTime = weekendOpenTime;
        this.weekendCloseTime = weekendCloseTime;
        this.storedCount = storedCount;
        this.cityId = cityId;
        this.latitude = latitude;
        this.longitude = longitude;
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
        this.cityId = requestDto.cityId();
    }
}
