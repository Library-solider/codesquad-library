package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class UnauthorizedJwtException extends BusinessException {
    public UnauthorizedJwtException() {
        super(ErrorCode.UNAUTHORIZED_JWT);
    }
}
