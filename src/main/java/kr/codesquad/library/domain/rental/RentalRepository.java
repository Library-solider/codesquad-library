package kr.codesquad.library.domain.rental;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByAccountAndIsReturnedFalse(Account account);

    Optional<Rental> findByBookAndAccountAndIsReturnedFalse(Book book, Account account);
}
