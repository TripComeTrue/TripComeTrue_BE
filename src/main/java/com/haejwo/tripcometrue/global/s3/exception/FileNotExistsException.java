package com.haejwo.tripcometrue.global.s3.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class FileNotExistsException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.FILE_NOT_EXISTS;

    public FileNotExistsException() {
        super(ERROR_CODE);
    }
}
