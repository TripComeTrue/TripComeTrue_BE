package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class NewPasswordSameAsOldException extends ApplicationException {

  private static final ErrorCode ERROR_CODE = ErrorCode.NEW_PASSWORD_SAME_AS_OLD;

  public NewPasswordSameAsOldException(){super(ERROR_CODE);}
}
