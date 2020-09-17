package kr.codesquad.library.global.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int status;
    private final String message;
    private final String code;

    private ErrorResponse(final ErrorCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
