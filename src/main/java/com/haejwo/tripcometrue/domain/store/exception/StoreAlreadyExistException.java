package com.haejwo.tripcometrue.domain.store.exception;
import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class StoreAlreadyExistException extends ApplicationException {


  public StoreAlreadyExistException(ErrorCode errorCode) {
    super(errorCode);
  }
}
