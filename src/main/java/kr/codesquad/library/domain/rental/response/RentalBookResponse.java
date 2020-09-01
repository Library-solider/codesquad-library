package kr.codesquad.library.domain.rental.response;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RentalBookResponse {

    private final String title;
    private final LocalDate beginDate;
    private final LocalDate returnDate;

    @Builder
    public RentalBookResponse(String title, LocalDate beginDate, LocalDate returnDate) {
        this.title = title;
        this.beginDate = beginDate;
        this.returnDate = returnDate;
    }

    public static RentalBookResponse of(Book book, Rental rental) {
        return RentalBookResponse.builder()
                .title(book.getTitle())
                .beginDate(rental.getBeginDate())
                .returnDate(rental.getReturnDate())
                .build();
    }
}
