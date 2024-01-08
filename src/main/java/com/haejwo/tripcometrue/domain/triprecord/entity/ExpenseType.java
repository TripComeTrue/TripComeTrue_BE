package com.haejwo.tripcometrue.domain.triprecord.entity;

import java.util.stream.Stream;

public enum ExpenseType {

    BELOW_50(50),
    BELOW_100(100),
    BELOW_200(200),
    BELOW_300(300),
    ABOVE_300(Integer.MAX_VALUE);

    private int max;

    ExpenseType(int max) {
        this.max = max;
    }

    public static ExpenseType findByMax(int max) {
        return Stream.of(ExpenseType.values())
                    .filter(p -> p.max == max)
                    .findFirst()
                    .orElseThrow(); // TODO: ExpenseType 예외 추가
    }

}
