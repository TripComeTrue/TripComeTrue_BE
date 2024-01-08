package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.domain.triprecord.exception.ExpenseRangeTypeNotFoundException;
import com.haejwo.tripcometrue.domain.triprecord.exception.TripRecordNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripRecordControllerAdvice {

    @ExceptionHandler(TripRecordNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> tripRecordNotFoundException(
        TripRecordNotFoundException e
    ) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler(ExpenseRangeTypeNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> expenseRangeTypeNotFoundException(
        ExpenseRangeTypeNotFoundException e
    ) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

}
