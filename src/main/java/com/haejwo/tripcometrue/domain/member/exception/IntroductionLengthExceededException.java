package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class IntroductionLengthExceededException extends ApplicationException {
  private static final ErrorCode ERROR_CODE = ErrorCode.INTRODUCTION_TOO_LONG;

  public IntroductionLengthExceededException(){super(ERROR_CODE);}

}
