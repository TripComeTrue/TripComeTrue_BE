package com.haejwo.tripcometrue.domain.likes.exception;
import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class InvalidLikesException extends ApplicationException {

  public InvalidLikesException(ErrorCode errorCode){
    super(errorCode);
  }

}
