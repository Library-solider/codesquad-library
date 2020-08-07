package kr.codesquad.library.domain.rental.firstclass;

import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.rental.Rental;
import lombok.Getter;

import java.util.List;

@Getter
public class Rentals {

    private final List<Rental> rentals;

    public Rentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public Rental find(Book book) {
        return rentals.isEmpty() ? null : rentals.get(drawByBook(book) - 1);
    }

    private int drawByBook(Book book) {
        return book.getRentals().size();
    }
}
