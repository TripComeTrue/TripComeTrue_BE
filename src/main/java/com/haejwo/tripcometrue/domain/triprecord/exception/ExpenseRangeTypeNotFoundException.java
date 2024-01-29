package com.haejwo.tripcometrue.domain.triprecord.exception;

import com.haejwo.tripcometrue.global.exception.ApplicationException;
import com.haejwo.tripcometrue.global.exception.ErrorCode;

public class ExpenseRangeTypeNotFoundException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.EXPENSE_RANGE_TYPE_NOT_FOUND;

    public ExpenseRangeTypeNotFoundException() {
        super(ERROR_CODE);
    }
}
