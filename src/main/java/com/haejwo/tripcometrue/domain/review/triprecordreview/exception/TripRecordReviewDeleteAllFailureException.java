package com.haejwo.tripcometrue.domain.review.triprecordreview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class TripRecordReviewDeleteAllFailureException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.TRIP_RECORD_REVIEW_DELETE_ALL_FAILURE;

    public TripRecordReviewDeleteAllFailureException() {
        super(ERROR_CODE);
    }
}
