package kr.codesquad.library.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " 유효하지 않은 값입니다."),
    METHOD_NOT_ALLOWED(405, "C002", " 서버에서 허용되지 않는 메소드입니다."),
    ENTITY_NOT_FOUND(400, "C003", " 존재하지 않은 엔티티입니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러"),
    HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다."),

    // Book
    CATEGORY_NOT_FOUND(404, "B001", "해당 카테고리는 없습니다."),
    BOOK_NOT_FOUND(404, "B002", "해당 도서는 없습니다."),
    PARAMETER_NOT_PRESENT(404, "B003", "q param이 필요합니다."),
    OUT_OF_BOOK(400, "B004", "해당 도서는 대여중입니다."),
    MAX_RENTAL_BOOK(400, "B005", "대여도서 수가 최대치입니다."),
    RENTAL_NOT_FOUND(404, "B006", "해당 대여는 없습니다."),
    REDUNDANT_RETURN_BOOK(400, "B007", "이미 반납한 도서입니다."),

    // Account
    ACCOUNT_NOT_FOUND(404, "A001", "해당 계정은 없습니다."),
    ACCOUNT_LOGOUT(400, "A002", "로그인이 필요합니다."),
    PROVIDER_NOT_FOUND(404, "A003", "존재하지 않는 OAuth 제공자 입니다."),
    AUTHORIZATION_FORBIDDEN(403, "A004", "해당 요청에 권한이 없습니다."),

    ;

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
