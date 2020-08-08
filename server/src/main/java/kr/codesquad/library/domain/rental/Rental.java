package kr.codesquad.library.domain.rental;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.book.Book;
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

    public String getAccountName() {
        return account.getName();
    }
}
