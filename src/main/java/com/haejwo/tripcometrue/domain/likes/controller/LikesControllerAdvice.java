package com.haejwo.tripcometrue.domain.likes.controller;

import com.haejwo.tripcometrue.domain.likes.exception.InvalidLikesException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LikesControllerAdvice {

  @ExceptionHandler(InvalidLikesException.class)
  public ResponseEntity<ResponseDTO<Void>> handleInvalidLikesException(InvalidLikesException e) {
    HttpStatus status = e.getErrorCode().getHttpStatus();

    return ResponseEntity
        .status(status)
        .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
  }
}
