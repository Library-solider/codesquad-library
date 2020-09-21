package kr.codesquad.library.service;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.domain.book.response.BookDetailResponse;
import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.book.response.BookSearchResponse;
import kr.codesquad.library.domain.rental.Rental;
import kr.codesquad.library.domain.rental.RentalRepository;
import kr.codesquad.library.global.error.exception.domain.AccountNotFoundException;
import kr.codesquad.library.global.error.exception.domain.BookNotFoundException;
import kr.codesquad.library.global.error.exception.domain.RentalNotFoundException;
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
        List<BookResponse> books = bookService.findByCategoryIdBooks(categoryId, page);

        //then
        assertThat(books.size()).isEqualTo(20);
    }

    @CsvSource({"3"})
    @ParameterizedTest
    public void 빌려가지_않은_도서상세정보_가져오기(Long id) {

        //when
        BookDetailResponse bookDetailResponse = bookService.findByBookId(id);

        //then
        assertAll(
                () -> assertThat(bookDetailResponse).isNotNull(),
                () -> assertThat(bookDetailResponse.isAvailable()).isTrue(),
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
        Rental rental = Rental.create(book, account);

        return rentalRepository.save(rental).getId();
    }
}
