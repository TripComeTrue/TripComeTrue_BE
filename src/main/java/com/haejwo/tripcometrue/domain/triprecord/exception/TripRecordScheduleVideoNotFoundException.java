package com.haejwo.tripcometrue.domain.triprecord.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class TripRecordScheduleVideoNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.TRIP_RECORD_SCHEDULE_VIDEO_NOT_FOUND;

    public TripRecordScheduleVideoNotFoundException() {
        super(ERROR_CODE);
    }
}
