package com.haejwo.tripcometrue.domain.triprecord.entity.type;

public enum HashTagType {

    COUPLE_TRIP("#연인끼리"),
    FRIEND_TRIP("#친구끼리"),
    SOLO_TRIP("#혼자여행"),
    FAMILY_TRIP("#가족여행"),
    BACKPACKING_TRIP("#배낭여행"),
    PAKAGE_TRIP("#패키지여행"),
    FREE_TRIP("#자유여행"),
    DAY_TRIP("#당일치기"),
    LONG_TRIP("#당일치기"),
    LOW_BUDGET("#저예산");

    private final String hashtag;

    HashTagType(String hashtag) {
        this.hashtag = hashtag;
    }

}
