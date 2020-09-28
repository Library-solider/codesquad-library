package kr.codesquad.library.domain.rental.response;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RentalBookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final String imageUrl;
    private final LocalDate returnDate;

    @Builder
    public RentalBookResponse(Long id, String title, String imageUrl, String author, LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.returnDate = returnDate;
    }

    public static RentalBookResponse of(Book book, Rental rental) {
        return RentalBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .imageUrl(book.getImageUrl())
                .returnDate(rental.getReturnDate())
                .build();
    }
}
