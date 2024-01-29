package com.haejwo.tripcometrue.domain.store.controller;
import com.haejwo.tripcometrue.domain.store.exception.StoreAlreadyExistException;
import com.haejwo.tripcometrue.domain.store.exception.StoreNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class StoreControllerAdvice {
  @ExceptionHandler(StoreNotFoundException.class)
  public ResponseEntity<ResponseDTO<Void>> StoreNotFoundExceptionHandler(StoreNotFoundException e) {

    HttpStatus status = e.getErrorCode().getHttpStatus();

    return ResponseEntity
        .status(status)
        .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
  }

  @ExceptionHandler(StoreAlreadyExistException.class)
  public ResponseEntity<ResponseDTO<Void>> StoreAlreadyExistExceptionHandler(StoreAlreadyExistException e) {

    HttpStatus status = e.getErrorCode().getHttpStatus();

    return ResponseEntity
        .status(status)
        .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
  }
}
