package com.haejwo.tripcometrue.domain.comment.placereview.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class PlaceReviewCommentNotFoundException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.PLACE_REVIEW_COMMENT_NOT_FOUND;

    public PlaceReviewCommentNotFoundException() {
        super(ERROR_CODE);
    }
}
