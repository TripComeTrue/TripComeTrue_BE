package com.haejwo.tripcometrue.domain.member.exception;


import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class UserInvalidAccessException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.USER_INVALID_ACCESS;

    public UserInvalidAccessException() {
        super(ERROR_CODE);
    }
}
