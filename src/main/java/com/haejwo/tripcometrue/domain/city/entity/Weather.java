package com.haejwo.tripcometrue.domain.city.entity;

import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Weather extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer month;

    private String maxAvgTemp;

    private String minAvgTemp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @Builder
    private Weather(Long id, City city, Integer month,
                    String maxAvgTemp, String minAvgTemp) {
        this.id = id;
        this.city = city;
        this.month = month;
        this.maxAvgTemp = maxAvgTemp;
        this.minAvgTemp = minAvgTemp;
    }
}
