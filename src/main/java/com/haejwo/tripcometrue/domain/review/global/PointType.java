package com.haejwo.tripcometrue.domain.review.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {

    ONLY_ONE_POINT(1),
    TWO_POINTS(2);

    private final int point;
}
