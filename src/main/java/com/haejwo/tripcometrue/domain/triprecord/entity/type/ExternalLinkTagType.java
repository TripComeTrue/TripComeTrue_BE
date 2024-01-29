package com.haejwo.tripcometrue.domain.triprecord.entity.type;

public enum ExternalLinkTagType {

    AIRLINE_TICKET_PURCHASE("항공권 구매 주소"),
    ACCOMMODATION_RESERVATION("숙박시설 예약 주소"),
    FOOD_TOURISM_LOCATION("음식점/관광시설 등의 위치주소"),
    TICKET_PASS_PURCHASE("티켓/입장권/패스 구매 주소");

    private final String name;

    ExternalLinkTagType(String name) {
        this.name = name;
    }
}
