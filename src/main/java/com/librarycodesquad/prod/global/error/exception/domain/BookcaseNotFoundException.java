package com.librarycodesquad.prod.global.error.exception.domain;

import com.librarycodesquad.prod.global.error.ErrorCode;
import com.librarycodesquad.prod.global.error.exception.EntityNotFoundException;

public class BookcaseNotFoundException extends EntityNotFoundException {
    public BookcaseNotFoundException() {
        super(ErrorCode.BOOKCASE_NOT_FOUND);
    }
}
