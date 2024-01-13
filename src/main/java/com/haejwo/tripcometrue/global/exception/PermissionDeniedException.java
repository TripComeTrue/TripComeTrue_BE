package com.haejwo.tripcometrue.global.exception;


public class PermissionDeniedException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.PERMISSION_DENIED;

    public PermissionDeniedException() {
        super(ERROR_CODE);
    }
}
