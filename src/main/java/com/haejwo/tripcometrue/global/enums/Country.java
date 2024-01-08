package com.haejwo.tripcometrue.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Country {

    KOREA("대한민국", Continent.KOREA),
    JAPAN("일본", Continent.ASIA),
    THAILAND("태국", Continent.ASIA),
    INDONESIA("인도네시아", Continent.ASIA),
    SINGAPORE("싱가포르", Continent.ASIA),

    USA("미국", Continent.AMERICA),
    CANADA("캐나다", Continent.AMERICA),

    FRANCE("프랑스", Continent.EUROPE),
    UNITED_KINGDOM("영국", Continent.EUROPE),
    ITALIA("이탈리아", Continent.EUROPE),
    GERMANY("독일", Continent.EUROPE),

    GUAM("괌", Continent.OCEANIA),
    NEW_ZEALAND("뉴질랜드", Continent.OCEANIA),
    AUSTRALIA("호주", Continent.OCEANIA);

    private final String description;
    private final Continent continent;
}
