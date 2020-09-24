package kr.codesquad.library.domain.rental.firstclass;

import kr.codesquad.library.domain.account.Account;
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
