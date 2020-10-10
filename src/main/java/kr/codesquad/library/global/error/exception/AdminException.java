package kr.codesquad.library.global.error.exception;

import kr.codesquad.library.global.error.ExceptionView;
import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {

    private final ExceptionView exceptionView;

    public AdminException(ExceptionView exceptionView) {
        super(exceptionView.getErrorMessage());
        this.exceptionView = exceptionView;
    }
}
