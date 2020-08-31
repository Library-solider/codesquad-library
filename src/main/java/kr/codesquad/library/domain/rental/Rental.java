package kr.codesquad.library.domain.rental;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.book.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
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

    @Column(name = "is_returned")
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

    public String getBookTitle() {
        return book.getTitle();
    }

    public static Rental create(Book book, Account account) {
        LocalDate now = LocalDate.now();
        return Rental.builder()
                .beginDate(now)
                .returnDate(now.plusDays(7))
                .book(book)
                .account(account)
                .isReturned(false)
                .build();
    }
}
