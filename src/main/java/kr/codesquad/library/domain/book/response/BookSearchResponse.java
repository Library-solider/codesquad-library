package kr.codesquad.library.domain.book.response;

import kr.codesquad.library.domain.book.Book;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookSearchResponse {

    private final Long bookCount;
    private final List<BookResponse> books;

    @Builder
    private BookSearchResponse(Long bookCount, List<BookResponse> books) {
        this.bookCount = bookCount;
        this.books = books;
    }

    public static BookSearchResponse of(Long bookCount, List<BookResponse> bookResponses) {
        return BookSearchResponse.builder()
                .bookCount(bookCount)
                .books(bookResponses)
                .build();
    }
}
