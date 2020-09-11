package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class BadRequestAuthorizationException extends BusinessException {
    public BadRequestAuthorizationException() {
        super(ErrorCode.BAD_REQUEST_AUTHORIZATION);
    }
}
