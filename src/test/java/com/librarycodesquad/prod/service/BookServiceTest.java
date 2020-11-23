package com.librarycodesquad.prod.service;

import com.librarycodesquad.prod.global.error.exception.domain.AccountNotFoundException;
import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.AccountRepository;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.book.BookRepository;
import com.librarycodesquad.prod.domain.book.response.BookDetailResponse;
import com.librarycodesquad.prod.domain.book.response.BookResponse;
import com.librarycodesquad.prod.domain.book.response.BookSearchResponse;
import com.librarycodesquad.prod.domain.rental.Rental;
import com.librarycodesquad.prod.domain.rental.RentalRepository;
import com.librarycodesquad.prod.global.error.exception.domain.BookNotFoundException;
import com.librarycodesquad.prod.global.error.exception.domain.RentalNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 각_카테고리_6개의_도서를_가져온다() {

        //when
        List<BookResponse> books = bookService.findTop6BooksByCategory(1L);

        //then
        assertThat(books.size()).isEqualTo(6);
    }

    @CsvSource({"1, 1"})
    @ParameterizedTest
    public void 각_카테고리별_도서를_페이지씩_가져온다(Long categoryId, int page) {

        //when
        List<BookResponse> books = bookService.findBooksByCategoryId(categoryId, page);

        //then
        assertThat(books.size()).isEqualTo(20);
    }

    @CsvSource({"1, 휴게실 - 1"})
    @ParameterizedTest
    public void 빌려가지_않은_도서상세정보_가져오기(Long bookId, String location) {

        //when
        BookDetailResponse bookDetailResponse = bookService.getBooksByBookId(bookId);

        //then
        assertAll(
                () -> assertThat(bookDetailResponse).isNotNull(),
                () -> assertThat(bookDetailResponse.isAvailable()).isTrue(),
                () -> assertThat(bookDetailResponse.getLocation()).isEqualTo(location),
                () -> assertThat(bookDetailResponse.getBookBorrower()).isNull()
        );
    }

    @CsvSource({"켄트 벡, 3, 1"})
    @ParameterizedTest
    public void 검색한_도서정보_가져오기(String searchWord, int result, int page) {

        //when
        BookSearchResponse bookSearchResponse = bookService.searchBooks(searchWord, page);

        //then
        assertAll(
                () -> assertThat(bookSearchResponse.getBooks()).isNotEmpty(),
                () -> assertThat(bookSearchResponse.getBookCount()).isEqualTo(result)
        );
    }

    @Transactional
    @CsvSource({"3, 2, 0"})
    @ParameterizedTest
    public void 도서_대여하기(Long bookId, Long accountId, int index) {

        //when
        bookService.rentalBook(bookId, accountId);
        List<Rental> rentalList = rentalRepository.findAll();
        Rental rental = rentalList.get(index);
        //then
        assertAll(
                () -> assertThat(rentalList).isNotEmpty(),
                () -> assertThat(rental.getAccount().getId()).isEqualTo(accountId),
                () -> assertThat(rental.getBook().getId()).isEqualTo(bookId)
        );
    }

    @Transactional
    @CsvSource({"1, 2"})
    @ParameterizedTest
    public void 도서_반납하기(Long bookId, Long accountId) {
        //given
        Long rentalId = saveRental(bookId, accountId);

        //when
        bookService.returnBook(bookId, accountId);
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(RentalNotFoundException::new);
        rental.returnBook();

        //then
        assertAll(
                () -> assertThat(rentalRepository.findAll()).isNotEmpty(),
                () -> assertThat(rental.isReturned()).isTrue()
        );
    }

    private Long saveRental(Long bookId, Long accountId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        Rental rental = Rental.createRental(book, account);

        return rentalRepository.save(rental).getId();
    }
}
