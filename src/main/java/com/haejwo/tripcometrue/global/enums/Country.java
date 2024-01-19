package com.haejwo.tripcometrue.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Country {

    KOREA("대한민국", Continent.KOREA,
        "https://a.cdn-hotels.com/gdcs/production74/d462/9fe21145-585c-4e7f-9373-24ed559ec010.jpg"),
    JAPAN("일본", Continent.ASIA,
        "https://www.state.gov/wp-content/uploads/2019/04/Japan-2107x1406.jpg"),
    THAILAND("태국", Continent.ASIA,
        "https://a.cdn-hotels.com/gdcs/production146/d585/aa60115c-bdfc-479f-88ba-5cb0f15a5af8.jpg?impolicy=fcrop&w=800&h=533&q=medium"),
    INDONESIA("인도네시아", Continent.ASIA,
        "https://media.timeout.com/images/105240189/750/422/image.jpg"),
    SINGAPORE("싱가포르", Continent.ASIA,
        "https://a.cdn-hotels.com/gdcs/production8/d1098/064a4e00-23ee-4137-8ec3-a0d27bca0782.jpg?impolicy=fcrop&w=800&h=533&q=medium"),

    USA("미국", Continent.AMERICA,
        "https://static.toiimg.com/photo/msid-84475066,width-96,height-65.cms"),
    CANADA("캐나다", Continent.AMERICA,
        "https://a.cdn-hotels.com/gdcs/production159/d204/01ae3fa0-c55c-11e8-9739-0242ac110006.jpg"),

    FRANCE("프랑스", Continent.EUROPE,
        "https://www.state.gov/wp-content/uploads/2023/07/shutterstock_667548661v2.jpg"),
    UNITED_KINGDOM("영국", Continent.EUROPE,
        "https://www.worldatlas.com/r/w1200/upload/c7/28/32/untitled-design-207.jpg"),
    ITALIA("이탈리아", Continent.EUROPE,
        "https://tourismmedia.italia.it/is/image/mitur/3200x1800_mete_turistiche_hub_autunno-1?wid=1600&hei=900&fit=constrain,1&fmt=webp"),
    GERMANY("독일", Continent.EUROPE,
        "https://www.state.gov/wp-content/uploads/2018/11/Germany-2109x1406.jpg"),

    GUAM("괌", Continent.OCEANIA,
        "https://a.cdn-hotels.com/gdcs/production49/d1519/6f89ae5d-542c-4fee-b333-a35761fe33d1.jpg"),
    NEW_ZEALAND("뉴질랜드", Continent.OCEANIA,
        "https://media.gq.com/photos/5ba1680236b2d004cdd843cd/16:9/w_2560%2Cc_limit/new-zealand-queenstown-travel-guide-gq.jpg"),
    AUSTRALIA("호주", Continent.OCEANIA,
        "https://i.natgeofe.com/k/b76526f3-bb84-489d-b229-56bcc08aa915/australia-sydney-opera-house.jpg?w=1084.125&h=611.625"),

    SOUTH_AFRICA("남아프리카 공화국", Continent.AFRICA,
        "https://media.istockphoto.com/id/620737858/de/foto/kapstadt-und-die-12-apostel-von-oben.jpg?s=612x612&w=0&k=20&c=GiUof-9yNuxdoPx_u1Yc9v8mwlaIFIvLbPFMVpMNMFE="),
    EGYPT("이집트", Continent.AFRICA,
        "https://e0.pxfuel.com/wallpapers/692/206/desktop-wallpaper-egypt-background-cool-for-me-egyptian.jpg");

    private final String description;
    private final Continent continent;
    private final String imageUrl;
}
