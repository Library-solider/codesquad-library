package com.librarycodesquad.prod.global.api;

import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final boolean status;

    private final int statusCode = 1;

    private final String statusMessage;

    private final T data;

    private ApiResult(boolean status, String statusMessage, T data) {
        this.status = status;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public static <T> ApiResult<T> OK(T data) {
        return new ApiResult<>(true, "OK", data);
    }

    public static ApiResult<Object> OK() {
        return new ApiResult<>(true, "OK", null);
    }
}
