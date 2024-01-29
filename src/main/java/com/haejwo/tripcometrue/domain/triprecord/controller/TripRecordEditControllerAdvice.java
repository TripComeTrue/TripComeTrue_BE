package com.haejwo.tripcometrue.domain.triprecord.controller;

import com.haejwo.tripcometrue.global.exception.PermissionDeniedException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripRecordEditControllerAdvice {

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ResponseDTO<Void>> PermissionDeniedException(
        PermissionDeniedException e
    ) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }
}
