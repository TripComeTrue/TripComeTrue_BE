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
    private CurrencyUnit currency;
    private String weatherRecommendation;
    private String weatherDescription;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Builder
    private City(
        Long id, String name, String language, String timeDifference,
        String voltage, String visa, CurrencyUnit currency, String weatherRecommendation,
        String weatherDescription, Country country
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
        this.country = country;
    }
}

