package com.haejwo.tripcometrue.domain.place.controller;

import com.haejwo.tripcometrue.domain.place.exception.PlaceNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlaceControllerAdvice {

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> placeNotFoundExceptionHandler(
        PlaceNotFoundException e
    ) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, e.getMessage()));


    }




}
