package com.librarycodesquad.admin.domain.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import com.librarycodesquad.prod.domain.category.Category;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class BookData {

    private String title;
    private String description;
    private String author;
    private String publisher;
    private String isbn;

    @JsonProperty("coverLargeUrl")
    private String imageUrl;

    @JsonProperty("pubDate")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate publicationDate;

    private Long categoryId;
    private Long bookcaseId;

    @Builder
    private BookData(String title, String description, String author, String publisher,
                     String isbn, String imageUrl, LocalDate publicationDate, Long categoryId, Long bookcaseId) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
        this.categoryId = categoryId;
        this.bookcaseId = bookcaseId;
    }

    public static BookData of(Book book, Category category, Bookcase bookcase) {
        return BookData.builder()
                       .title(book.getTitle())
                       .description(book.getDescription())
                       .author(book.getAuthor())
                       .publisher(book.getPublisher())
                       .isbn(book.getIsbn())
                       .imageUrl(book.getImageUrl())
                       .publicationDate(book.getPublicationDate())
                       .categoryId(category.getId())
                       .bookcaseId(bookcase.getId())
                       .build();
    }
}
