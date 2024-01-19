package com.haejwo.tripcometrue.domain.city.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CurrencyUnit {

    AED("아랍에미리트 디르함", 1),
    AUD("호주 달러", 1),
    BHD("바레인 디나르", 1),
    BND("브루나이 달러", 1),
    CAD("캐나다 달러", 1),
    CHF("스위스 프랑", 1),
    CNH("위안화", 1),
    DKK("덴마아크 크로네", 1),
    EUR("유로", 1),
    GBP("영국 파운드", 1),
    HKD("홍콩 달러", 1),
    IDR("인도네시아 루피아", 100),
    JPY("일본 엔", 100),
    KWD("쿠웨이트 디나르", 1),
    MYR("말레이시아 링기트", 1),
    NLG("네덜란드 길더", 1),
    NOK("노르웨이 크로네", 1),
    NZD("뉴질랜드 달러", 1),
    SAR("사우디 리얄", 1),
    SEK("스웨덴 크로나", 1),
    SGD("싱가포르 달러", 1),
    THB("태국 바트", 1),
    USD("미국 달러", 1),
    ZAR("남아프라카공화국 랜드", 1),
    EGP("이집트 파운드", 1);

    private final String currencyName;
    private final Integer standard;
}
