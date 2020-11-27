package com.librarycodesquad.prod.domain.rental;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByAccountAndIsReturnedFalse(Account account);

    Optional<Rental> findByBookAndAccountAndIsReturnedFalse(Book book, Account account);

    Optional<Rental> findByBookIdAndIsReturnedFalse(Long bookId);
}
