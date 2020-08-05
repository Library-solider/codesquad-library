package kr.codesquad.library.global.api;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResult<T> {

    private final boolean status;

    private final int statusCode;

    private final String statusMessage;

    private final T data;

    private ApiResult(boolean status, int statusCode, String statusMessage, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public static <T> ApiResult<T> OK(T data) {
        return new ApiResult<>(true, 0, "OK", data);
    }
}
