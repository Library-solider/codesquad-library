package kr.codesquad.library.admin.domain.book;

import kr.codesquad.library.domain.book.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookSummaryResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final String categoryTitle;
    private final String isbn;

    @Builder
    private BookSummaryResponse(Long id, String title, String author, String categoryTitle, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryTitle = categoryTitle;
        this.isbn = isbn;
    }

    public static BookSummaryResponse from(Book book) {
        return BookSummaryResponse.builder()
                                  .id(book.getId())
                                  .title(book.getTitle())
                                  .author(book.getAuthor())
                                  .categoryTitle(book.getCategory().getTitle())
                                  .isbn(book.getIsbn())
                                  .build();
    }
}
