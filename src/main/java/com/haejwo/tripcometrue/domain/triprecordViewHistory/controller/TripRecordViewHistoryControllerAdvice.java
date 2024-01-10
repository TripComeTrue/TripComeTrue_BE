package com.haejwo.tripcometrue.domain.triprecordViewHistory.controller;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripRecordViewHistoryControllerAdvice {

  @ExceptionHandler(TripRecordNotFoundException.class)
  public ResponseEntity<ResponseDTO<Void>> tripRecordNotFoundExceptionHandler(TripRecordNotFoundException e) {
    HttpStatus status = e.getErrorCode().getHttpStatus();

    return ResponseEntity
        .status(status)
        .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
  }

}
