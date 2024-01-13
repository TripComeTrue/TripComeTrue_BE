package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class CurrentPasswordNotMatchException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.CURRENT_PASSWORD_NOT_MATCH;

    public CurrentPasswordNotMatchException(){super(ERROR_CODE);}
}
