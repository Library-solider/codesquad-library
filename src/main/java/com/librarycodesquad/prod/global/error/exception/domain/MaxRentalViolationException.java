package com.librarycodesquad.prod.global.error.exception.domain;

import com.librarycodesquad.prod.global.error.ErrorCode;
import com.librarycodesquad.prod.global.error.exception.BusinessException;

public class MaxRentalViolationException extends BusinessException {
    public MaxRentalViolationException() {
        super(ErrorCode.MAX_RENTAL_BOOK);
    }
}
