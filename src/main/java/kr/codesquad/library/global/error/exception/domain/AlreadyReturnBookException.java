package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class AlreadyReturnBookException extends BusinessException {
    public AlreadyReturnBookException() {
        super(ErrorCode.ALREADY_RETURN_BOOK);
    }
}
