package com.haejwo.tripcometrue.global.s3.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class FileMaxSizeExceededException extends ApplicationException {

    private final static ErrorCode ERROR_CODE = ErrorCode.MAX_SIZE_EXCEEDED;

    public FileMaxSizeExceededException() {
        super(ERROR_CODE);
    }
}
