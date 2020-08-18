package kr.codesquad.library.domain.book.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookSearchResponse {

    private final Integer bookCount;
    private final List<BookResponse> books;

    @Builder
    public BookSearchResponse(Integer bookCount, List<BookResponse> books) {
        this.bookCount = bookCount;
        this.books = books;
    }
}
