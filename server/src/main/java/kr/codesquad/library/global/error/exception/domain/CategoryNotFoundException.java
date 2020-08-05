package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.exception.EntityNotFoundException;

import static kr.codesquad.library.global.error.ErrorCode.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends EntityNotFoundException {

    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
    }
}
