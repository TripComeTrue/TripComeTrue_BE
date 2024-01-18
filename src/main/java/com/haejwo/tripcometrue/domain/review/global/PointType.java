package com.haejwo.tripcometrue.domain.review.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {

    ONLY_CONTENT_POINT(1),
    CONTENT_WITH_IMAGE_POINT(2);

    private final int point;
}
