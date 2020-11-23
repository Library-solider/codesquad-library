package com.librarycodesquad.prod.global.error.exception;

import com.librarycodesquad.prod.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
