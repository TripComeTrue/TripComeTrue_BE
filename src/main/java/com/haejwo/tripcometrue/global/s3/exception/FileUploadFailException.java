package com.haejwo.tripcometrue.global.s3.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class FileUploadFailException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.FILE_UPLOAD_FAIL;

    public FileUploadFailException() {
        super(ERROR_CODE);
    }
}
