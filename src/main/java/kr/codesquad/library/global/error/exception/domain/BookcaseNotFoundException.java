package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.EntityNotFoundException;

public class BookcaseNotFoundException extends EntityNotFoundException {
    public BookcaseNotFoundException() {
        super(ErrorCode.BOOKCASE_NOT_FOUND);
    }
}
