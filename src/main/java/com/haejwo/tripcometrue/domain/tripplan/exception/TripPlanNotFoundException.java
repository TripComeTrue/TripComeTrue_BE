package com.haejwo.tripcometrue.domain.tripplan.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class TripPlanNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.TRIP_PLAN_NOT_FOUND;

    public TripPlanNotFoundException() {
        super(ERROR_CODE);
    }
}
