package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class MaxRentalViolationException extends BusinessException {
    public MaxRentalViolationException() {
        super(ErrorCode.MAX_RENTAL_BOOK);
    }
}
