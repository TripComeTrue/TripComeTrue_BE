package com.haejwo.tripcometrue.domain.place.entity;

import com.haejwo.tripcometrue.domain.place.dto.request.PlaceRequestDto;
import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String description;
    private LocalTime weekdayOpenTime;
    private LocalTime weekdayCloseTime;
    private LocalTime weekendOpenTime;
    private LocalTime weekendCloseTime;
    private Integer storedCount;

    // 임시 City 테이블 데이터
    private Long cityId;

    @Builder
    public Place(
        Long id, String name, String address, String description,
        LocalTime weekdayOpenTime, LocalTime weekdayCloseTime,
        LocalTime weekendOpenTime, LocalTime weekendCloseTime,
        Integer storedCount, Long cityId, String city) {
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
