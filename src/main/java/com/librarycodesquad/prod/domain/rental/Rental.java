package com.librarycodesquad.prod.domain.rental;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.book.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "is_returned", nullable = false)
    private boolean isReturned;

    @Builder
    private Rental(Long id, LocalDate beginDate, LocalDate returnDate, Book book, Account account, boolean isReturned) {
        this.id = id;
        this.beginDate = beginDate;
        this.returnDate = returnDate;
        this.book = book;
        this.account = account;
        this.isReturned = isReturned;
    }

    public String getAccountName() {
        return account.getName();
    }

    public Long findAccountId() { return account.getId(); }

    // 반납일수는 14일(2주)
    public static Rental createRental(Book book, Account account) {
        LocalDate now = LocalDate.now();
        return Rental.builder()
                .beginDate(now)
                .returnDate(now.plusDays(14))
                .book(book)
                .account(account)
                .isReturned(false)
                .build();
    }

    public void returnBook() {
        this.isReturned = true;
        book.returnBook();
    }
}
