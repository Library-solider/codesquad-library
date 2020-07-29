package kr.codesquad.library.global.api;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiResult<T> {

    private final boolean status;

    private final int statusCode;

    private final HttpStatus statusMessage;

    private final T response;

    private final ApiError error;

    private ApiResult(boolean status, int statusCode, HttpStatus statusMessage, T response, ApiError error) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.response = response;
        this.error = error;
    }

    public static <T> ApiResult<T> OK(T response) {
        return new ApiResult<>(true, HttpStatus.OK.value(), HttpStatus.OK, response, null);
    }

//    public static ApiResult ERROR(Exception exception, HttpStatus status) {
//        return new ApiResult<>(false, null, new ApiError(exception, status));
//    }
//
//    public static ApiResult ERROR(String errorMessage, HttpStatus status) {
//        return new ApiResult<>(false, null, new ApiError(errorMessage, status));
//    }
}
