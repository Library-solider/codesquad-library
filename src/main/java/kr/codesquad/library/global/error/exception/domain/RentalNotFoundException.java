package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class RentalNotFoundException extends BusinessException {
    public RentalNotFoundException() {
        super(ErrorCode.RENTAL_NOT_FOUND);
    }
}
