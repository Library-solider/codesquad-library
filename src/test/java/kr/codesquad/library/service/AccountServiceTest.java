package kr.codesquad.library.service;

import kr.codesquad.library.domain.account.response.AccountMyPageResponse;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.book.BookRepository;
import kr.codesquad.library.global.error.exception.domain.BookNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @CsvSource({"2, 1, 2, 3, 3, 0"})
    @ParameterizedTest
    @Transactional
    public void 마이페이지_확인하기(Long accountId, Long book1, Long book2, Long book3, int size, int first) {
        //given
        Book book = bookRepository.findById(book1).orElseThrow(BookNotFoundException::new);
        bookService.rentalBookByUser(book1, accountId);
        bookService.rentalBookByUser(book2, accountId);
        bookService.rentalBookByUser(book3, accountId);

        //when
        AccountMyPageResponse myPage = accountService.getMyPage(accountId);
        String bookTitle = myPage.getRentalBookResponse().get(first).getTitle();

        //then
        assertAll(
                () -> assertThat(myPage).isNotNull(),
                () -> assertThat(myPage.getRentalBookResponse()).isNotEmpty(),
                () -> assertThat(myPage.getRentalBookResponse().size()).isEqualTo(size),
                () -> assertThat(bookTitle).isEqualTo(book.getTitle())
        );
    }
}
