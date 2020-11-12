package com.librarycodesquad.prod.global.error.exception.domain;

import com.librarycodesquad.prod.global.error.ErrorCode;
import com.librarycodesquad.prod.global.error.exception.BusinessException;

public class RentalNotFoundException extends BusinessException {
    public RentalNotFoundException() {
        super(ErrorCode.RENTAL_NOT_FOUND);
    }
}
