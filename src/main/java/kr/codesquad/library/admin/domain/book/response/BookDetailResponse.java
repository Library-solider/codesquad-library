package kr.codesquad.library.admin.domain.book.response;

import kr.codesquad.library.domain.book.Book;
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
    private final String available;
    private final int recommendCount;
    private final String categoryTitle;
    private final String location;

    @Builder
    private BookDetailResponse(String title, String description, String author, String publisher,
                               LocalDate publicationDate, String isbn, String imageUrl,
                               boolean available, int recommendCount, String categoryTitle, String location) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.available = mapToAvailable(available);
        this.recommendCount = recommendCount;
        this.categoryTitle = categoryTitle;
        this.location = location;
    }

    public static BookDetailResponse from(Book book) {
        return BookDetailResponse.builder()
                                 .title(book.getTitle())
                                 .description(book.getDescription())
                                 .author(book.getAuthor())
                                 .publisher(book.getPublisher())
                                 .publicationDate(book.getPublicationDate())
                                 .isbn(book.getIsbn())
                                 .imageUrl(book.getImageUrl())
                                 .available(book.isAvailable())
                                 .recommendCount(book.getRecommendCount())
                                 .categoryTitle(book.getCategory().getTitle())
                                 .location(book.getBookcase().getLocation())
                                 .build();
    }

    private String mapToAvailable(boolean available) {
        if (available) { return "Y"; }
        return "N";
    }
}
