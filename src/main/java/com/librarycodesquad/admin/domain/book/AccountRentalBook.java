package com.librarycodesquad.admin.domain.book;

import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AccountRentalBook {

    private final Long id;
    private final String title;
    private final LocalDate returnDate;

    @Builder
    private AccountRentalBook(Long id, String title, LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.returnDate = returnDate;
    }

    public static AccountRentalBook of(Book book, Rental rental) {
        return AccountRentalBook.builder()
                .id(book.getId())
                .title(book.getTitle())
                .returnDate(rental.getReturnDate())
                .build();
    }
}
