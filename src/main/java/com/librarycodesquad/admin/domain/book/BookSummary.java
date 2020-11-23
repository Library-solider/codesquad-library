package com.librarycodesquad.admin.domain.book;

import com.librarycodesquad.prod.domain.book.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookSummary {

    private final Long id;
    private final String title;
    private final String categoryTitle;
    private final String location;
    private final String isbn;

    @Builder
    private BookSummary(Long id, String title, String categoryTitle, String location, String isbn) {
        this.id = id;
        this.title = title;
        this.categoryTitle = categoryTitle;
        this.location = location;
        this.isbn = isbn;
    }

    public static BookSummary from(Book book) {
        return BookSummary.builder()
                          .id(book.getId())
                          .title(book.getTitle())
                          .categoryTitle(book.getCategory().getTitle())
                          .location(book.getBookcase().getLocation())
                          .isbn(book.getIsbn())
                          .build();
    }
}
