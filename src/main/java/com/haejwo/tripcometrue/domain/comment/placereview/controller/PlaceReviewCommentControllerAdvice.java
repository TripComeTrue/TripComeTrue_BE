package com.haejwo.tripcometrue.domain.comment.placereview.controller;

import com.haejwo.tripcometrue.domain.comment.placereview.exception.PlaceReviewCommentNotFoundException;
import com.haejwo.tripcometrue.global.util.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceReviewCommentControllerAdvice {

    @ExceptionHandler(PlaceReviewCommentNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handlePlaceReviewCommentNotFoundException(PlaceReviewCommentNotFoundException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();

        return ResponseEntity
                .status(status)
                .body(ResponseDTO.errorWithMessage(status, e.getMessage()));
    }
}
