package kr.codesquad.library.global.error;

import lombok.Getter;

@Getter
public enum ExceptionView {

    DELETE_ENTITY_DENITED("exception/delete-denied", "도서가 남아 있어서 삭제가 불가능 합니다. 먼저 도서를 완전히 비우고 다시 시도 해주세요!");

    private final String viewName;
    private final String errorMessage;

    ExceptionView(String viewName, String errorMessage) {
        this.viewName = viewName;
        this.errorMessage = errorMessage;
    }
}
