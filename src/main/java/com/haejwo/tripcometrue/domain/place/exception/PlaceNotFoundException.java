package com.haejwo.tripcometrue.domain.place.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class PlaceNotFoundException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.PLACE_NOT_FOUND;

    public PlaceNotFoundException() {
        super(ERROR_CODE);
    }

}
