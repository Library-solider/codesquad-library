package kr.codesquad.library.global.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiError {

    private final String message;

    private final int status;

    ApiError(Exception exception, HttpStatus status) {
        this(exception.getMessage(), status);
    }

    ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }
}
