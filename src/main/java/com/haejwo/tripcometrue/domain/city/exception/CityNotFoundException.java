package com.haejwo.tripcometrue.domain.city.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class CityNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CITY_NOT_FOUND;

    public CityNotFoundException() {
        super(ERROR_CODE);
    }
}
