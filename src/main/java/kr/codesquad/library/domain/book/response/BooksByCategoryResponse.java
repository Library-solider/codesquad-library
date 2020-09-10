package kr.codesquad.library.domain.book.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BooksByCategoryResponse {

    private final Long categoryId;
    private final String categoryTitle;
    private final Integer bookCount;
    private final List<BookResponse> books;

    @Builder
    private BooksByCategoryResponse(Long categoryId, String categoryTitle, Integer bookCount, List<BookResponse> books) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.bookCount = bookCount;
        this.books = books;
    }
}
