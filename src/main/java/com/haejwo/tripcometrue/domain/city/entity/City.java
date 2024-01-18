package com.haejwo.tripcometrue.domain.city.entity;

import com.haejwo.tripcometrue.global.entity.BaseTimeEntity;
import com.haejwo.tripcometrue.global.enums.Country;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class City extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;
    private String name;
    private String language;
    private String timeDifference;
    private String voltage;
    private String visa;
    @Column(length = 1000)
    private String weatherRecommendation;
    @Column(length = 2500)
    private String weatherDescription;
    private Integer storeCount;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CurrencyUnit currency;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Builder
    private City(
        Long id, String name, String language, String timeDifference,
        String voltage, String visa, CurrencyUnit currency, String weatherRecommendation,
        String weatherDescription, Integer storeCount, Country country, String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.timeDifference = timeDifference;
        this.voltage = voltage;
        this.visa = visa;
        this.currency = currency;
        this.weatherRecommendation = weatherRecommendation;
        this.weatherDescription = weatherDescription;
        this.storeCount = storeCount;
        this.country = country;
        this.imageUrl = imageUrl;
    }

    @PrePersist
    public void prePersist() {
        this.storeCount = 0;
    }
}

