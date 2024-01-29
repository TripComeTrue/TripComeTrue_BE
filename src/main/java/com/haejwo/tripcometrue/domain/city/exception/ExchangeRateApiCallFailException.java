package com.haejwo.tripcometrue.domain.city.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class ExchangeRateApiCallFailException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.EXCHANGE_RATE_API_FAIL;
    public ExchangeRateApiCallFailException() {
        super(ERROR_CODE);
    }

    public ExchangeRateApiCallFailException(String message) {
        super(ERROR_CODE, message);
    }
}
