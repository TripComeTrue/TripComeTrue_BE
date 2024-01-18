package com.haejwo.tripcometrue.domain.member.controller;

import com.haejwo.tripcometrue.domain.member.exception.*;
import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(value = {
        EmailDuplicateException.class,
    })
    public ResponseEntity<ResponseDTO<Void>> handleUserControllerExceptions(
        RuntimeException exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof EmailDuplicateException) {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(
            ResponseDTO.errorWithMessage(status, exception.getMessage()));
    }

    @ExceptionHandler(UserInvalidAccessException.class)
    public ResponseEntity<ResponseDTO<Void>> userInvalidAccessException(
        UserInvalidAccessException e
    ) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler({CurrentPasswordNotMatchException.class, NewPasswordSameAsOldException.class, NewPasswordNotMatchException.class,
        EmailNotMatchException.class})
    public ResponseEntity<ResponseDTO<Void>> handleApplicationException(ApplicationException exception) {
        HttpStatus status = exception.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleUserNotFoundException(UserNotFoundException exception) {
        HttpStatus status = exception.getErrorCode().getHttpStatus();

        return ResponseEntity
            .status(status)
            .body(ResponseDTO.errorWithMessage(status, exception.getMessage()));
    }
}

