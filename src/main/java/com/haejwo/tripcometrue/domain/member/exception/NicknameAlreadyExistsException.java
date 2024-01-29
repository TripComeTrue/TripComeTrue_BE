package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class NicknameAlreadyExistsException extends ApplicationException {

  private static final ErrorCode ERROR_CODE = ErrorCode.NICKNAME_ALREADY_EXISTS;

  public NicknameAlreadyExistsException(){super(ERROR_CODE);}
}
