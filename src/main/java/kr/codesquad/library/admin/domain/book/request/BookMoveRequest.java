package kr.codesquad.library.admin.domain.book.request;

import lombok.Getter;

import java.util.List;

@Getter
public class BookMoveRequest {

    private List<Long> bookIds;
    private Long categoryId;
    private Long bookcaseId;

    private BookMoveRequest(List<Long> bookIds, Long categoryId, Long bookcaseId) {
        this.bookIds = bookIds;
        this.categoryId = categoryId;
        this.bookcaseId = bookcaseId;
    }

    public static BookMoveRequest of(List<Long> bookIds, Long categoryId, Long bookcaseId) {
        return new BookMoveRequest(bookIds, categoryId, bookcaseId);
    }
}
