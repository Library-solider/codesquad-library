package kr.codesquad.library.domain.book.response;

import lombok.Getter;

import java.util.List;

import static kr.codesquad.library.domain.book.vo.CategoryBookVO.MOBILE_BOOK;

@Getter
public class MobileBooksResponse {

    private final Long categoryId;
    private final String categoryTitle = MOBILE_BOOK;
    private final List<BookResponse> books;

    public MobileBooksResponse(Long categoryId, List<BookResponse> books) {
        this.categoryId = categoryId;
        this.books = books;
    }
}
