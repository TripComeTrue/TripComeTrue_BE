package com.haejwo.tripcometrue.domain.review.triprecordreview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class DuplicateTripRecordReviewException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.REGISTERING_DUPLICATE_TRIP_RECORD_REVIEW;

    public DuplicateTripRecordReviewException() {
        super(ERROR_CODE);
    }
}
