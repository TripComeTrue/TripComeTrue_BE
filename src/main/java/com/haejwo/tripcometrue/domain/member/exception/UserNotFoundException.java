package com.haejwo.tripcometrue.domain.member.exception;


import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class UserNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException() {
        super(ERROR_CODE);
    }
}
