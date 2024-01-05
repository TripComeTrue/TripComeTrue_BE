package com.haejwo.tripcometrue.global.s3.controller;

import com.haejwo.tripcometrue.global.s3.exception.FileEmptyException;
import com.haejwo.tripcometrue.global.s3.exception.FileUploadFailException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class S3ControllerAdvice {

    @ExceptionHandler(FileUploadFailException.class)
    public ResponseEntity<ResponseDTO<Void>> fileUploadExceptionHandler(FileUploadFailException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<ResponseDTO<Void>> fileEmptyExceptionHandler(FileEmptyException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

}
