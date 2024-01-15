package com.haejwo.tripcometrue.domain.review.placereview.controller;

import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewAlreadyExistsException;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewDeleteAllFailureException;
import com.haejwo.tripcometrue.domain.review.placereview.exception.PlaceReviewNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlaceReviewControllerAdvice {

    @ExceptionHandler(PlaceReviewNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handlePlaceReviewNotFoundException(PlaceReviewNotFoundException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler(PlaceReviewAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<Void>> handlePlaceReviewAlreadyExistsException(PlaceReviewAlreadyExistsException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler(PlaceReviewDeleteAllFailureException.class)
    public ResponseEntity<ResponseDTO<Void>> handlePlaceReviewDeleteAllFailureException(PlaceReviewDeleteAllFailureException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }
}
