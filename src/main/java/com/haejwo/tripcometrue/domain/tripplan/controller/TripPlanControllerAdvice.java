package com.haejwo.tripcometrue.domain.tripplan.controller;

import com.haejwo.tripcometrue.domain.tripplan.exception.TripPlanNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripPlanControllerAdvice {

    @ExceptionHandler(TripPlanNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> TripPlanNotFoundException(
        TripPlanNotFoundException e
    ) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }
}
