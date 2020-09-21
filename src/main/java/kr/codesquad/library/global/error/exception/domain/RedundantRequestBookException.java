package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class RedundantRequestBookException extends BusinessException {
    public RedundantRequestBookException() {
        super(ErrorCode.REDUNDANT_RETURN_BOOK);
    }
}
