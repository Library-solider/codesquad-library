package com.librarycodesquad.admin.domain.book.response;

import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookDetailResponse {

    private final String title;
    private final String description;
    private final String author;
    private final String publisher;
    private final LocalDate publicationDate;
    private final String isbn;
    private final String imageUrl;
    private final Long borrowerId;
    private final String borrowerName;
    private final int recommendCount;
    private final String categoryTitle;
    private final String location;

    @Builder
    private BookDetailResponse(String title, String description, String author, String publisher,
                               LocalDate publicationDate, String isbn, String imageUrl, Long borrowerId,
                               String borrowerName, int recommendCount, String categoryTitle, String location) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.recommendCount = recommendCount;
        this.categoryTitle = categoryTitle;
        this.location = location;
    }

    public static BookDetailResponse of(Book book, Rental rental) {
        BookDetailResponseBuilder builder = BookDetailResponse.builder();
        builder.title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .isbn(book.getIsbn())
                .imageUrl(book.getImageUrl())
                .recommendCount(book.getRecommendCount())
                .categoryTitle(book.getCategory().getTitle())
                .location(book.getBookcase().getLocation())
                .build();
        if (book.isAvailable()) {
            return builder.build();
        }
        return builder.borrowerId(rental.findAccountId())
                      .borrowerName(rental.getAccountName())
                      .build();
    }
}
