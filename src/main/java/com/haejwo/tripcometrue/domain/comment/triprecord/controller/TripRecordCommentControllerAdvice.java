package com.haejwo.tripcometrue.domain.comment.triprecord.controller;

import com.haejwo.tripcometrue.domain.comment.triprecord.exception.TripRecordCommentNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripRecordCommentControllerAdvice {

    @ExceptionHandler(TripRecordCommentNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleTripRecordCommentNotFoundException(TripRecordCommentNotFoundException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }
}
