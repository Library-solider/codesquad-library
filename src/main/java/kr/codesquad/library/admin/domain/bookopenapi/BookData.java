package kr.codesquad.library.admin.domain.bookopenapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.codesquad.library.domain.book.Book;
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

    @Builder
    private BookData(String title, String description, String author, String publisher,
                     String isbn, String imageUrl, LocalDate publicationDate) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
    }

    public static BookData from(Book book) {
        return BookData.builder()
                       .title(book.getTitle())
                       .description(book.getDescription())
                       .author(book.getAuthor())
                       .publisher(book.getPublisher())
                       .isbn(book.getIsbn())
                       .imageUrl(book.getImageUrl())
                       .publicationDate(book.getPublicationDate())
                       .build();
    }
}
