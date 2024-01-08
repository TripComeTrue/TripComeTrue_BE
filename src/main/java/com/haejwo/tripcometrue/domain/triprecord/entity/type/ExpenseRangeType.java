package com.haejwo.tripcometrue.domain.triprecord.entity.type;

import com.haejwo.tripcometrue.domain.triprecord.exception.ExpenseRangeTypeNotFoundException;
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
                    .orElseThrow(ExpenseRangeTypeNotFoundException::new);
    }

}
