package com.librarycodesquad.admin.domain.book.response;

import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BookDetailResponse {

    private final String title;
    private final String description;
    private final String author;
    private final String publisher;
    private final LocalDate publicationDate;
    private final String isbn;
    private final String imageUrl;
    private final String borrower;
    private final int recommendCount;
    private final String categoryTitle;
    private final String location;

    @Builder
    private BookDetailResponse(String title, String description, String author, String publisher,
                               LocalDate publicationDate, String isbn, String imageUrl,
                               String borrower, int recommendCount, String categoryTitle, String location) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.borrower = borrower;
        this.recommendCount = recommendCount;
        this.categoryTitle = categoryTitle;
        this.location = location;
    }

    public static BookDetailResponse of(Book book, List<Rental> rentalList) {
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
        if (rentalList.isEmpty()) {
            return builder.borrower("없습니다").build();
        }
        // 대여자는 1명이기 때문에 0을 사용하여 대여자를 가져옵니다.
        builder.borrower(rentalList.get(0).getAccountName());
        return builder.build();
    }
}
