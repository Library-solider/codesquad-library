package kr.codesquad.library.admin.domain.book.request;

import lombok.Getter;

import java.util.List;

@Getter
public class BookMoveRequest {

    private List<Long> bookIds;
    private Long categoryId;
    private Long bookcaseId;
}
