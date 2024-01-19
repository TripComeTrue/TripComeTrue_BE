package com.haejwo.tripcometrue.domain.review.triprecordreview.controller;

import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.TripRecordReviewAlreadyExistsException;
import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.TripRecordReviewDeleteAllFailureException;
import com.haejwo.tripcometrue.domain.review.triprecordreview.exception.TripRecordReviewNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripRecordReviewControllerAdvice {

    @ExceptionHandler(TripRecordReviewAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<Void>> handleTripRecordReviewAlreadyExistsException(TripRecordReviewAlreadyExistsException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler(TripRecordReviewNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleTripRecordReviewNotFoundException(TripRecordReviewNotFoundException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }

    @ExceptionHandler(TripRecordReviewDeleteAllFailureException.class)
    public ResponseEntity<ResponseDTO<Void>> handleTripRecordReviewDeleteAllFailureException(TripRecordReviewDeleteAllFailureException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }
}
