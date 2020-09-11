package kr.codesquad.library.global.error.exception.oauth;

import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.exception.BusinessException;

public class ProviderNotExistException extends BusinessException {
    public ProviderNotExistException() {
        super(ErrorCode.PROVIDER_NOT_FOUND);
    }
}
