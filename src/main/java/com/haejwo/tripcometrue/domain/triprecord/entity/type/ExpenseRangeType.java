package com.haejwo.tripcometrue.domain.triprecord.entity.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.haejwo.tripcometrue.domain.triprecord.exception.ExpenseRangeTypeNotFoundException;
import java.util.Objects;
import java.util.stream.Stream;

public enum ExpenseRangeType {

    BELOW_50(50),
    BELOW_100(100),
    BELOW_200(200),
    BELOW_300(300),
    ABOVE_300(Integer.MAX_VALUE);

    private final Integer max;

    ExpenseRangeType(Integer max) {
        this.max = max;
    }

    public static ExpenseRangeType findByMax(Integer max) {
        return Stream.of(ExpenseRangeType.values())
                    .filter(p -> Objects.equals(p.max, max))
                    .findFirst()
                    .orElseThrow(ExpenseRangeTypeNotFoundException::new);
    }

    @JsonCreator
    public static ExpenseRangeType parse(String inputValue) {
        return Stream.of(ExpenseRangeType.values())
            .filter(e -> {
                    String replacedInputValue = inputValue.replaceAll(" ", "")
                        .replaceAll("_", "");

                    String replacedExpenseRangeType = e.name().replaceAll("_", "");

                    return replacedExpenseRangeType.equals(replacedInputValue.toUpperCase());
                }
            )
            .findFirst()
            .orElse(null);
    }

}
