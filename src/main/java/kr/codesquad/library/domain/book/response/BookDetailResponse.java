package kr.codesquad.library.domain.book.response;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookDetailResponse {

    private final boolean available;
    private final String title;
    private final String author;
    private final String imageUrl;
    private final String description;
    private final String publisher;
    private final LocalDate publicationDate;
    private final String isbn;
    private final int recommendCount;
    private final String bookBorrower;

    @Builder
    private BookDetailResponse(boolean available, String title, String author,
                              String imageUrl, String description, String publisher,
                              LocalDate publicationDate, String isbn, int recommendCount, String bookBorrower) {
        this.available = available;
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.recommendCount = recommendCount;
        this.bookBorrower = bookBorrower;
    }

    public static BookDetailResponse of(Book book, Rental rental) {
        BookDetailResponseBuilder builder = BookDetailResponse.builder();
                builder.available(book.isAvailable())
                .title(book.getTitle())
                .author(book.getAuthor())
                .imageUrl(book.getImageUrl())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .isbn(book.getIsbn())
                .recommendCount(book.getRecommendCount())
                .build();
        if (book.isAvailable()) {
            return builder.build();
        }

        builder.bookBorrower(rental.getAccountName());
        return builder.build();
    }
}
