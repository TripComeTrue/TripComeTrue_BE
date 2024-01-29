package com.haejwo.tripcometrue.domain.review.placereview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class PlaceReviewAlreadyExistsException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PLACE_REVIEW_ALREADY_EXISTS;

    public PlaceReviewAlreadyExistsException() {
        super(ERROR_CODE);
    }
}
