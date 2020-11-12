package com.librarycodesquad.prod.global.error.exception.oauth;

import com.librarycodesquad.prod.global.error.ErrorCode;
import com.librarycodesquad.prod.global.error.exception.BusinessException;

public class ProviderNotExistException extends BusinessException {
    public ProviderNotExistException() {
        super(ErrorCode.PROVIDER_NOT_FOUND);
    }
}
