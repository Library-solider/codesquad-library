package com.librarycodesquad.prod.global.error.exception.domain;

import com.librarycodesquad.prod.global.error.ErrorCode;
import com.librarycodesquad.prod.global.error.exception.BusinessException;

public class RedundantRequestBookException extends BusinessException {
    public RedundantRequestBookException() {
        super(ErrorCode.REDUNDANT_RETURN_BOOK);
    }
}
