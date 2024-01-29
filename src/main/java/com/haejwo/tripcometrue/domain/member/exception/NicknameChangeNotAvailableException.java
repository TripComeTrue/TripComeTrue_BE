package com.haejwo.tripcometrue.domain.member.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class NicknameChangeNotAvailableException extends ApplicationException {

  private static ErrorCode ERROR_CODE = ErrorCode.NICKNAME_CHANGE_NOT_AVAILABLE;

  public NicknameChangeNotAvailableException(){
    super(ERROR_CODE);
  }

}
