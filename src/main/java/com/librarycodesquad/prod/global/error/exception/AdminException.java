package com.librarycodesquad.prod.global.error.exception;

import com.librarycodesquad.prod.global.error.ExceptionView;
import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {

    private final ExceptionView exceptionView;

    public AdminException(ExceptionView exceptionView) {
        super(exceptionView.getErrorMessage());
        this.exceptionView = exceptionView;
    }
}
