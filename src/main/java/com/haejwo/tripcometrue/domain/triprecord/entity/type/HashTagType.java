package com.haejwo.tripcometrue.domain.triprecord.entity.type;

public enum HashTagType {

    COUPLE_TRIP("#연인끼리"),
    BACKPACKING("#배낭여행"),
    LOW_BUDGET("#저예산");

    private final String hashtag;

    HashTagType(String hashtag) {
        this.hashtag = hashtag;
    }

}
