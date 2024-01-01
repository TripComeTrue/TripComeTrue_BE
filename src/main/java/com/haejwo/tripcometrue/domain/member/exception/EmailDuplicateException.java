package com.haejwo.tripcometrue.domain.member.exception;


import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class EmailDuplicateException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.USER_ALREADY_REGISTERED;

    public EmailDuplicateException() {

        super(ERROR_CODE);
    }
}
