package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class OutOfBookException extends BusinessException {
    public OutOfBookException() {
        super(ErrorCode.OUT_OF_BOOK);
    }
}
