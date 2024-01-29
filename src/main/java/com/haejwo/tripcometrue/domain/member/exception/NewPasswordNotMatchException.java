package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class NewPasswordNotMatchException extends ApplicationException {
  private static final ErrorCode ERROR_CODE = ErrorCode.NEW_PASSWORD_NOT_MATCH;

  public NewPasswordNotMatchException(){super(ERROR_CODE);}

}
