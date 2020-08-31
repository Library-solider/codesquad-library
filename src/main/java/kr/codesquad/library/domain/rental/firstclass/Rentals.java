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

    public static Rentals of(List<Rental> rentals) {
        return new Rentals(rentals);
    }

    public Rental findByBook(Book book) {
        return rentals.isEmpty() ? null : rentals.get(drawByBook(book));
    }

    public Rental findByAccount(Account account) {
        return rentals.isEmpty() ? null : rentals.get(drawByAccount(account));
    }

    private int drawByBook(Book book) {
        return book.getRentals().size() - 1;
    }

    private int drawByAccount(Account account) {
        return account.getRentals().size() - 1;
    }
}
