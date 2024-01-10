package com.haejwo.tripcometrue.domain.store.exception;
import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class StoreNotFoundException extends ApplicationException {

  public StoreNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
