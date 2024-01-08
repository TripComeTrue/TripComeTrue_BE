package com.haejwo.tripcometrue.domain.triprecord.entity;

import java.util.stream.Stream;

public enum ExpenseRangeType {

    BELOW_50(50),
    BELOW_100(100),
    BELOW_200(200),
    BELOW_300(300),
    ABOVE_300(Integer.MAX_VALUE);

    private final int max;

    ExpenseRangeType(int max) {
        this.max = max;
    }

    public static ExpenseRangeType findByMax(int max) {
        return Stream.of(ExpenseRangeType.values())
                    .filter(p -> p.max == max)
                    .findFirst()
                    .orElseThrow(); // TODO: ExpenseType 예외 추가
    }

}
