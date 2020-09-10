package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.exception.EntityNotFoundException;

import static kr.codesquad.library.global.error.ErrorCode.BOOK_NOT_FOUND;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException() {
        super(BOOK_NOT_FOUND);
    }
}
