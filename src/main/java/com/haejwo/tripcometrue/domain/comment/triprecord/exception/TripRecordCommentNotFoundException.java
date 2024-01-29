package com.haejwo.tripcometrue.domain.comment.triprecord.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class TripRecordCommentNotFoundException extends ApplicationException {
    private static final ErrorCode ERROR_CODE = ErrorCode.TRIP_RECORD_COMMENT_NOT_FOUND;

    public TripRecordCommentNotFoundException() {
        super(ERROR_CODE);
    }
}
