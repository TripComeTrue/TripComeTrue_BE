package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class EmailNotMatchException extends ApplicationException {
  private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_NOT_MATCH;

  public EmailNotMatchException(){super(ERROR_CODE);}
}
