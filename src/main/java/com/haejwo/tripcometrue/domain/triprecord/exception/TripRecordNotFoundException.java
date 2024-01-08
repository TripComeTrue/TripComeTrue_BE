package com.haejwo.tripcometrue.domain.triprecord.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class TripRecordNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.TRIP_RECORD_NOT_FOUND;

    public TripRecordNotFoundException() {
        super(ERROR_CODE);
    }
}
