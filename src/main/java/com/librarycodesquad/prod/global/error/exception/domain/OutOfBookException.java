package com.librarycodesquad.prod.global.error.exception.domain;

import com.librarycodesquad.prod.global.error.ErrorCode;
import com.librarycodesquad.prod.global.error.exception.BusinessException;

public class OutOfBookException extends BusinessException {
    public OutOfBookException() {
        super(ErrorCode.OUT_OF_BOOK);
    }
}
