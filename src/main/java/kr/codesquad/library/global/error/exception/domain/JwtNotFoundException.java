package kr.codesquad.library.global.error.exception.domain;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class JwtNotFoundException extends BusinessException {
    public JwtNotFoundException() {
        super(ErrorCode.JWT_NOT_FOUND);
    }
}
