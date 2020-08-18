package kr.codesquad.library.domain.book.response;

import kr.codesquad.library.domain.book.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
public class BookResponse {

    private final Long id;
    private final String imageUrl;
    private final String title;
    private final String author;
    private final String publisher;
    private final int recommendCount;
    private final LocalDate publicationDate;

    @Builder
    public BookResponse(Long id, String imageUrl, String author, String title, String publisher,
                        int recommendCount, LocalDate publicationDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.recommendCount = recommendCount;
        this.publicationDate = publicationDate;
    }

    public static BookResponse of(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .imageUrl(book.getImageUrl())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .recommendCount(book.getRecommendCount())
                .build();
    }
}
