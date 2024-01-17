package com.haejwo.tripcometrue.domain.review.triprecordreview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class TripRecordReviewAlreadyExistsException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.TRIP_RECORD_REVIEW_ALREADY_EXISTS;

    public TripRecordReviewAlreadyExistsException() {
        super(ERROR_CODE);
    }
}
