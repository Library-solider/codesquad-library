package kr.codesquad.library.domain.book.response;

import kr.codesquad.library.domain.category.Category;
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

    public static BooksByCategoryResponse from(Category category, List<BookResponse> bookResponses) {
        return BooksByCategoryResponse.builder()
                .categoryId(category.getId())
                .categoryTitle(category.getTitle())
                .bookCount(category.getBooks().size())
                .books(bookResponses)
                .build();
    }
}
