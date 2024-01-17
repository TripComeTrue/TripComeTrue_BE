package com.haejwo.tripcometrue.domain.review.placereview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class PlaceReviewDeleteAllFailureException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PLACE_REVIEW_DELETE_ALL_FAILURE;

    public PlaceReviewDeleteAllFailureException() {
        super(ERROR_CODE);
    }
}
