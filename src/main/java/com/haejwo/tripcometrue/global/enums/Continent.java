package com.haejwo.tripcometrue.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Continent {

    ASIA("아시아"),
    AFRICA("아프리카"),
    AMERICA("아메리카"),
    EUROPE("유럽"),
    OCEANIA("오세아니아"),
    KOREA("대한민국");

    private final String description;
}
