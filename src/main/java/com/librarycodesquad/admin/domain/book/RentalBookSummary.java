package com.librarycodesquad.admin.domain.book;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RentalBookSummary {

    private final Long id;
    private final String title;
    private final String borrower;
    private final String isbn;
    private final LocalDate returnDate;

    @Builder
    private RentalBookSummary(Book book, Account account, Rental rental) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.borrower = account.getName();
        this.isbn = book.getIsbn();
        this.returnDate = rental.getReturnDate();
    }

    public static RentalBookSummary from(Rental rental) {
        return RentalBookSummary.builder()
                                .book(rental.getBook())
                                .account(rental.getAccount())
                                .rental(rental)
                                .build();
    }
}
