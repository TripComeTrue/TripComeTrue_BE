package com.haejwo.tripcometrue.domain.review.triprecordreview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class ContentNotInitializedException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.CAN_NOT_MODIFYING_WITHOUT_CONTENT;

    public ContentNotInitializedException() {
        super(ERROR_CODE);
    }
}
