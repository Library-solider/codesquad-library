package com.librarycodesquad.prod.domain.rental.firstclass;

import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import lombok.Getter;

import java.util.List;

@Getter
public class Rentals {

    private final List<Rental> rentals;

    public Rentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public static Rentals from(List<Rental> rentals) {
        return new Rentals(rentals);
    }

    public Rental findByBook(Book book) {
        return rentals.isEmpty() ? null : rentals.get(drawRentalListByBook(book));
    }

    private int drawRentalListByBook(Book book) {
        return book.getRentals().size() - 1;
    }
}
